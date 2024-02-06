package com.example.airdns.domain.reservation.servicefacade;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoService;
import com.example.airdns.domain.payment.service.PaymentService;
import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.service.ReservationService;
import com.example.airdns.domain.restschedule.service.RestScheduleService;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.exception.UsersExceptionCode;
import com.example.airdns.domain.user.service.UsersService;
import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ReservationServiceFacadeImplV1 implements ReservationServiceFacade {

    private final UsersService usersService;
    private final RoomsService roomsService;
    private final ReservationService reservationService;
    private final RestScheduleService restScheduleService;
    private final PaymentService paymentService;
    private final DeleteInfoService deleteInfoService;
    private final RedissonClient redissonClient;


    @Override
    public ReservationResponseDto.CreateReservationResponseDto createReservation(
            Long userId,
            Long roomId,
            ReservationRequestDto.CreateReservationRequestDto requestDto) {

        RLock lock = redissonClient.getLock(roomId.toString());
        try {
            boolean available = lock.tryLock(10, 5, TimeUnit.SECONDS);
            if(!available) {
                throw new ReservationCustomException(ReservationExceptionCode.LOCKED_REDISSON);
            }

            Users users = usersService.findById(userId);
            Rooms rooms = roomsService.findById(roomId);

            LocalDateTime checkInTime = requestDto.getCheckInTime().truncatedTo(ChronoUnit.HOURS);
            LocalDateTime checkOutTime = requestDto.getCheckOutTime().truncatedTo(ChronoUnit.HOURS);

            isValidatedRequestSchedule(rooms, checkInTime, checkOutTime);

            BigDecimal price = calculateReservationPrice(rooms.getPrice(), checkInTime, checkOutTime);
            String name = createReservationName(rooms.getName(), checkInTime, checkOutTime);

            Reservation reservation = requestDto.toEntity(users, rooms, price, name);
            return ReservationResponseDto.CreateReservationResponseDto.from(reservationService.save(reservation));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ReservationResponseDto.ReadReservationResponseDto readReservation(
            Long userId,
            Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);

        if (!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.FORBIDDEN_YOUR_NOT_COME_IN);
        }

        return ReservationResponseDto.ReadReservationResponseDto.from(reservation);
    }

    @Override
    public Page<ReservationResponseDto.ReadReservationResponseDto> readReservationList(
            Long usersId,
            Pageable pageable) {
        return reservationService.
                findAllByUsersId(usersId, pageable).
                map(ReservationResponseDto.ReadReservationResponseDto::from);
    }

    @Override
    @Transactional
    public void deleteReservation(
            Long userId,
            Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);

        if (!Objects.equals(reservation.getUsers().getId(), userId)
                && !Objects.equals(reservation.getRooms().getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.FORBIDDEN_YOUR_NOT_COME_IN);
        }

        reservation.cancelled();
    }

    @Override
    public Page<ReservationResponseDto.ReadReservationResponseDto> readRoomReservationList(
            Long roomsId,
            Pageable pageable) {
        return reservationService.
                findAllByRoomsIdAndIsCancelledFalse(roomsId, pageable).
                map(ReservationResponseDto.ReadReservationResponseDto::from);
    }

    @Override
    public BigDecimal calculateReservationPrice(BigDecimal roomsPrice, LocalDateTime checkIn, LocalDateTime checkOut) {
        long betweenTime = ChronoUnit.HOURS.between(checkIn, checkOut);
        return BigDecimal.valueOf(roomsPrice.longValue() * betweenTime);
    }

    @Override
    public String createReservationName(String roomsName, LocalDateTime checkIn, LocalDateTime checkOut) {
        return roomsName + "," +checkIn + "," + checkOut;
    }

    private void isValidatedRequestSchedule(
            Rooms rooms,
            LocalDateTime checkIn,
            LocalDateTime checkOut) {
        LocalDateTime now = LocalDateTime.now();
        if (checkIn.isBefore(now)) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_CHECK_IN_IS_BEFORE_NOW);
        }
        else if(checkIn.isAfter(checkOut)){
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_CHECK_IN_IS_AFTER_CHECK_OUT);
        }

        if (checkIn.isEqual(checkOut)) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_CHECK_IN_IS_SAME_CHECK_OUT);
        }

        if (reservationService.isReserved(rooms, checkIn, checkOut)
                || isRested(rooms, checkIn, checkOut)) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_NOT_RESERVE);
        }
    }
    private boolean isRested(
            Rooms rooms,
            LocalDateTime checkIn,
            LocalDateTime checkOut) {
        return restScheduleService.hasRestScheduleInRoomBetweenTimes(rooms, checkOut, checkIn);
    }

    @Override
    public void deleteReservation(LocalDateTime deleteTime){
        // 삭제할 Reservation의 ID 조회
        List<Long> reservationIds = reservationService.findReservationIds(deleteTime);

        for (Long reservationId : reservationIds) {
            // 연관된 Payments의 ID 조회
            List<Long> paymentIds = paymentService.findPaymentIdsByReservationId(reservationId);

            // DeleteInfo 저장
            saveDeleteReservationInfo(reservationId);
            paymentIds.forEach(paymentService::saveDeletedPaymentInfo);

            // 연관된 Payments 삭제
            paymentService.deleteByReservationId(reservationId);

            // Reservation 삭제
            reservationService.deleteReservation(reservationId);
        }
    }
    private void saveDeleteReservationInfo(Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        deleteInfoService.saveDeletedReservationInfo(reservation);
    }
}

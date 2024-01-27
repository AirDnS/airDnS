package com.example.airdns.domain.reservation.servicefacade;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ReservationServiceFacadeImplV1 implements ReservationServiceFacade {

    private final UsersService usersService;
    private final RoomsService roomsService;
    private final ReservationService ReservationService;
    private final RestScheduleService restScheduleService;
    @Override
    public void createReservation(Long userId,
                                  Long roomId,
                                  ReservationRequestDto.CreateReservationRequestDto requestDto) {
        Users users = usersService.findById(userId);
        Rooms rooms = roomsService.findById(roomId);

        isValidatedRequestSchedule(roomId, requestDto.getCheckInTime(), requestDto.getCheckOutTime());

        Reservation reservation = requestDto.toEntity(users, rooms);
        ReservationService.save(reservation);
    }


    @Override
    public ReservationResponseDto.ReadReservationResponseDto readReservation(Long userId,
                                                                             Long reservationId) {
        Reservation reservation = ReservationService.findById(reservationId);

        if (!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.FORBIDDEN_YOUR_NOT_COME_IN);
        }

        return ReservationResponseDto.ReadReservationResponseDto.from(reservation);
    }

    @Override
    public List<ReservationResponseDto.ReadReservationResponseDto> readReservationList(Long userId) {
        return ReservationService.
                findAllByUsersId(userId).
                stream().
                map(ReservationResponseDto.ReadReservationResponseDto::from).
                toList();
    }

    @Override
    @Transactional
    public void deleteReservation(Long userId,
                                  Long reservationId) {
        Reservation reservation = ReservationService.findById(reservationId);

        if (!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.FORBIDDEN_YOUR_NOT_COME_IN);
        }

        reservation.cancelled();
    }

    @Override
    public void isValidatedRequestSchedule(Long roomId,
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

        if (ReservationService.isReserved(roomId, checkIn, checkOut)
                || isRested(roomId, checkIn, checkOut)) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_NOT_RESERVE);
        }
    }

    private boolean isRested(Long roomId,
                            LocalDateTime checkIn,
                            LocalDateTime checkOut) {
        return restScheduleService.hasRestScheduleInRoomBetweenTimes(roomId, checkOut, checkIn);
    }

}

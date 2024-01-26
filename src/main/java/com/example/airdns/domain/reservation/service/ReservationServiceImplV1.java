package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.restschedule.repository.RestScheduleRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.exception.UsersExceptionCode;
import com.example.airdns.domain.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationServiceImplV1 implements ReservationService {

    private final UsersService usersService;
    private final RoomsService roomsService;
    private final ReservationRepository reservationRepository;
    private final RestScheduleRepository restScheduleRepository;

    @Override
    public void createReservation(Long userId,
                                  Long roomId,
                                  ReservationRequestDto.CreateReservationRequestDto requestDto) {
        Users users = usersService.findById(userId);
        Rooms rooms = roomsService.findById(roomId);

        isValidatedRequestSchedule(roomId, requestDto.getCheckInTime(), requestDto.getCheckOutTime());

        Reservation reservation = requestDto.toEntity(users, rooms);
        reservationRepository.save(reservation);
    }


    @Override
    public ReservationResponseDto.ReadReservationResponseDto readReservation(Long userId,
                                                                             Long reservationId) {
        Reservation reservation = findById(reservationId);

        if (!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.FORBIDDEN_YOUR_NOT_COME_IN);
        }

        return ReservationResponseDto.ReadReservationResponseDto.from(reservation);
    }

    @Override
    public List<ReservationResponseDto.ReadReservationResponseDto> readReservationList(Long userId) {
        return reservationRepository.
                findAllByUsersId(userId).
                stream().
                map(ReservationResponseDto.ReadReservationResponseDto::from).
                toList();
    }

    @Override
    @Transactional
    public void deleteReservation(Long userId,
                                  Long reservationId) {
        Reservation reservation = findById(reservationId);

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

        if (isReserved(roomId, checkIn, checkOut)
                || isRested(roomId, checkIn, checkOut)) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_NOT_RESERVE);
        }
    }

    @Override
    public Reservation findById(Long reservationId) {
        return reservationRepository.findByIdAndIsCancelledFalse(reservationId).orElseThrow(
                () -> new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION));
    }

    @Override
    public boolean isReserved(Long roomId,
                              LocalDateTime checkIn,
                              LocalDateTime checkOut) {
        return reservationRepository.
                findFirstByRoomsIdAndIsCancelledFalseAndCheckInBeforeAndCheckOutAfter(roomId, checkOut, checkIn).
                isPresent();
    }

    @Override
    public boolean isRested(Long roomId,
                            LocalDateTime checkIn,
                            LocalDateTime checkOut) {
        return restScheduleRepository.findFirstByRoomsIdAndStartTimeBeforeAndEndTimeAfter(roomId, checkOut, checkIn).
                isPresent();
    }

}

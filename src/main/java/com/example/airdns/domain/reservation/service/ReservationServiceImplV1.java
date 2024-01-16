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
//import com.example.airdns.domain.user.service.UsersService;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.exception.UsersExceptionCode;
import com.example.airdns.domain.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
                                  ReservationRequestDto.CreateReservationDto requestDto) {
        Users users = usersService.findById(userId);
        Rooms rooms = roomsService.findById(roomId);
        LocalDateTime now = LocalDateTime.now();

        if (requestDto.getCheckInTime().isBefore(now)
                || requestDto.getCheckInTime().isAfter(requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_REQUEST);
        }
        if (requestDto.getCheckInTime().isEqual(requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_REQUEST);
        }

        if (isReserved(roomId, requestDto.getCheckInTime(), requestDto.getCheckOutTime())
                || isRested(roomId, requestDto.getCheckInTime(), requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_NOT_RESERVE);
        }

        Reservation reservation = requestDto.toEntity(users, rooms);
        reservationRepository.save(reservation);
    }


    @Transactional
    @Override
    public ReservationResponseDto.UpdateReservationResponseDto updateReservation(Long userId,
                                                                        Long roomsId,
                                                                        Long reservationId,
                                                                        ReservationRequestDto.UpdateReservationDto requestDto) {
        Reservation reservation = findById(reservationId);

        if(!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.FORBIDDEN_YOUR_NOT_COME_IN);
        }

        LocalDateTime now = LocalDateTime.now();

        if (requestDto.getCheckInTime().isBefore(now)
                || requestDto.getCheckInTime().isAfter(requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_REQUEST);
        }

        if (requestDto.getCheckInTime().isEqual(requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_REQUEST);
        }

        if (isReserved(roomsId, requestDto.getCheckInTime(), requestDto.getCheckOutTime())
                || isRested(roomsId, requestDto.getCheckInTime(), requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION_NOT_RESERVE);
        }
        reservation.updateReservationTime(requestDto);
        return ReservationResponseDto.UpdateReservationResponseDto.of(reservation);
    }

    @Override
    public ReservationResponseDto.ReadReservationResponseDto readReservation(Long userId, Long reservationId) {
        Reservation reservation = findById(reservationId);

        if(!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new UsersCustomException(UsersExceptionCode.BAD_REQUEST_NOT_MATCH_AUTH_CODE);
        }

        return ReservationResponseDto.ReadReservationResponseDto.of(reservation);
    }

    @Override
    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(
                () -> new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION));
    }

    @Override
    public boolean isReserved(Long roomsId, LocalDateTime checkIn, LocalDateTime checkOut) {
        return reservationRepository.findFirstByRoomsIdAndCheckInBeforeAndCheckOutAfter(roomsId, checkOut, checkIn).isPresent();
    }

    @Override
    public boolean isRested(Long roomsId, LocalDateTime checkIn, LocalDateTime checkOut) {
        return restScheduleRepository.findFirstByRoomsIdAndRestStartTimeBeforeAndRestEndTimeAfter(roomsId, checkOut, checkIn).isPresent();
    }

}

package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.room.entity.Rooms;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImplV1 implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    public Reservation findById(Long reservationId) {
        return reservationRepository.findByIdAndIsCancelledFalse(reservationId).orElseThrow(
                () -> new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION));
    }

    @Override
    public boolean isReserved(Rooms rooms,
                              LocalDateTime checkIn,
                              LocalDateTime checkOut) {
        return reservationRepository.
                findFirstByRoomsAndIsCancelledFalseAndCheckInBeforeAndCheckOutAfter(rooms, checkOut, checkIn).
                isPresent();
    }

    @Override
    public Page<Reservation> findAllByUsersId(Long userId, Pageable pageable) {
        return reservationRepository.findAllByUsersId(userId, pageable);
    }

    @Override
    public Page<Reservation> findAllByRoomsIdAndIsCancelledFalse(Long roomsId, Pageable pageable) {
        return reservationRepository.findAllByRoomsIdAndIsCancelledFalse(roomsId, pageable);
    }

}

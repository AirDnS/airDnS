package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoService;
import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.reservation.repository.ReservationRepositoryQueryImpl;
import com.example.airdns.domain.room.entity.Rooms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImplV1 implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final DeleteInfoService deleteInfoService;
    private final ReservationRepositoryQueryImpl reservationRepositoryQuery;

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
    public List<Reservation> findAllByUsersId(Long userId) {
        return reservationRepository.findAllByUsersId(userId);
    }

    @Override
    public List<Long> findDeletedReservationIds(QReservation qReservation, Long userId){
        return reservationRepositoryQuery.findDeletedReservationIds(qReservation, userId);
    }

    @Override
    public void saveDeletedReservationInfo(Long reservationId){
        Reservation reservation = findById(reservationId);
        deleteInfoService.saveDeletedReservationInfo(reservation);
    }

    @Override
    public void deleteByUserId(QReservation qReservation, Long userId){
        reservationRepositoryQuery.deleteByUserId(qReservation, userId);
    }
}

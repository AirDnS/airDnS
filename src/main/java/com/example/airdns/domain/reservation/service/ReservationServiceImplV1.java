package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoService;
import com.example.airdns.domain.payment.service.PaymentService;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.reservation.repository.ReservationRepositoryQuery;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
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
    private final DeleteInfoService deleteInfoService;

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

    @Override
    public List<Long> findReservationIdsByUserId(Long userId){
        return reservationRepository.findReservationIdsByUserId(userId);
    }

    @Override
    public void saveDeletedReservationInfo(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                // 삭제된 Room
                ()-> new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION)
        );
        deleteInfoService.saveDeletedReservationInfo(reservation);
    }

    @Override
    public void deleteByUserId(Long userId){
        reservationRepository.deleteByUserId(userId);
    }

    @Override
    public List<Long> findReservationIdsByRoomId(Long roomId){
        return reservationRepository.findReservationIdsByRoomId(roomId);
    }

    @Override
    public void deleteByRoomId(Long roomId){
        reservationRepository.deleteByRoomId(roomId);
    }

    @Override
    public List<Long> findReservationIds(LocalDateTime deleteTime){
        return reservationRepository.findReservationIds(deleteTime);
    }

    @Override
    public void deleteReservation(Long reservationId){
        reservationRepository.deleteReservationInfo(reservationId);
    }
}

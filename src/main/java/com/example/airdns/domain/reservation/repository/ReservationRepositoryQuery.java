package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.room.entity.QRooms;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepositoryQuery {
    List<Long> findReservationIdsByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Long> findReservationIdsByRoomId(Long roomId);

    void deleteByRoomId(Long roomId);

    List<Long> findReservationIds(LocalDateTime deleteTime);

    void deleteReservationInfo(Long reservationId);
}

package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.room.entity.QRooms;

import java.util.List;

public interface ReservationRepositoryQuery {
    List<Long> findDeletedReservationIds(QReservation qReservation, Long userId);

    void deleteByUserId(QReservation qReservation, Long userId);
}

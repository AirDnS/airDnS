package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    /**
     * 해당 ID 값에 해당하는 예약정보를 찾는다.
     * @Param id 예약 id
     * @return 예약 정보를 반환한다.
     */
    Optional<Reservation> findById(Long id);

    /**
     * DB에 새로운 예약정보를 저장한다.
     * @Param 저장할 Reservation 객체
     * @return Reservation
     */

    Reservation save(Reservation reservation);
}

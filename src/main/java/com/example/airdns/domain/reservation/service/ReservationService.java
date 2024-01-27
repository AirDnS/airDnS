package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    /**
     * 예약을 생성한다
     * @param reservation 예약 객체
     * @return 생성된 예약 객체
     */
    Reservation save(Reservation reservation);

    /**
     * 예약을 삭제한다
     * @param reservation 예약 객체
     */
    void delete(Reservation reservation);

    /**
     * 해당 번호에 대한 예약을 찾는다.
     * @Param reservationId 예약번호
     * @return 예약정보
     */
    Reservation findById(Long reservationId);

    /**
     * 해당 시간에 해당 방에 예약이 있는지 없는지 확인
     * @Param 방 번호
     * @Param 체크인 시간
     * @Param 체크아웃 시간
     * @return true/false
     */
    boolean isReserved(
            Rooms rooms,
            LocalDateTime checkIn,
            LocalDateTime checkOut
    );

    List<Reservation> findAllByUsersId(Long userId);
}

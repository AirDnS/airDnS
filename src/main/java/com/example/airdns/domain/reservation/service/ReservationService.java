package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    /**
     * 예약을 생성한다
     *
     * @param reservation 예약 객체
     * @return 생성된 예약 객체
     */
    Reservation save(Reservation reservation);

    /**
     * 예약을 삭제한다
     *
     * @param reservation 예약 객체
     */
    void delete(Reservation reservation);

    /**
     * 해당 번호에 대한 예약을 찾는다.
     *
     * @return 예약정보
     * @Param reservationId 예약번호
     */
    Reservation findById(Long reservationId);

    /**
     * 해당 시간에 해당 방에 예약이 있는지 없는지 확인
     *
     * @return true/false
     * @Param 방 번호
     * @Param 체크인 시간
     * @Param 체크아웃 시간
     */
    boolean isReserved(
            Rooms rooms,
            LocalDateTime checkIn,
            LocalDateTime checkOut
    );

    /**
     * 해당 유저에 대한 예약 목록 조회
     *
     * @return 예약 목록 페이지 타입
     * @Param 유저 Id
     * @Param 페이지 옵션
     */
    Page<Reservation> findAllByUsersId(Long usersId, Pageable pageable);

    /**
     * 해당 방에 대한 예약 목록 조회
     *
     * @return 예약 목록 리스트 타입
     * @Param 방 번호
     * @Param 페이지 옵션
     */
    Page<Reservation> findAllByRoomsIdAndIsCancelledFalse(Long roomsId, Pageable pageable);

    void saveDeletedReservationInfo(Long reservationId);

    void deleteByUserId(Long userId);

    void deleteByRoomId(Long roomId);

    List<Long> findReservationIdsByRoomId(Long roomId);

    List<Long> findReservationIds(LocalDateTime deleteTime);

    List<Long> findReservationIdsByUserId(Long userId);

    void deleteReservation(Long reservationId);
}

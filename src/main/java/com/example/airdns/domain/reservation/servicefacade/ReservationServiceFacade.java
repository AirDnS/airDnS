package com.example.airdns.domain.reservation.servicefacade;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationServiceFacade {

    /**
     * 해당 방에 대해 예약한다.
     * @Param user 예약 유저
     * @Param roomId 예약 할 방
     * @Param request 예약할 날짜에 관한 정보
     * @return void
     */
    void createReservation(
            Long userId,
            Long roomId,
            ReservationRequestDto.CreateReservationRequestDto createReservationDto
    );

    /**
     * 해당 예약 정보를 조회한다.
     * @Param 유저 Id
     * @Param 해당 예약 Id
     * @return 예약 정보
     */
    ReservationResponseDto.ReadReservationResponseDto readReservation(
            Long userId,
            Long reservationId
    );

    /**
     * 유저의 예약 목록을 조회한다.
     * @Param 유저 Id
     * @return 예약 정보 목록
     */
    List<ReservationResponseDto.ReadReservationResponseDto> readReservationList(
            Long id
    );

    /**
     * 해당 예약을 취소한다.
     * @Param 유저 Id
     * @Param 예약 Id
     * @return void
     */
    void deleteReservation(
            Long userId,
            Long reservationId
    );

}

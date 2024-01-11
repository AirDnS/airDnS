package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;

public interface ReservationService {

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
            ReservationRequestDto.CreateReservationDto createReservationDto
    );


}

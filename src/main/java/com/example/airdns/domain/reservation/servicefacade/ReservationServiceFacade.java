package com.example.airdns.domain.reservation.servicefacade;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import org.springframework.data.domain.Pageable;

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
     * @Param 페이지
     * @Param 페이지에 개수
     * @Param 정렬 기준
     * @Param 정렬 방법
     * @return 예약 정보 목록
     */
    List<ReservationResponseDto.ReadReservationResponseDto> readReservationList(
            Long usersId,
            Pageable pageable
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

    /**
     * 해당 예약을 취소한다.
     * @Param 방 Id
     * @return 예약 정보 목록
     */
    List<ReservationResponseDto.ReadReservationResponseDto> readRoomReservationList(Long roomsId);

    void deleteReservation(LocalDateTime deleteTime);
}

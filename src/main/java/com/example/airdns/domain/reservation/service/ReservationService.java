package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.user.entity.Users;

import java.time.LocalDateTime;
import java.util.List;

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
    boolean isReserved(Long roomsId, LocalDateTime checkIn, LocalDateTime checkOut);

    /**
     * 해당 시간에 해당 방이 영업하는지 안하는지 확인
     * @Param 방 번호
     * @Param 체크인 시간
     * @Param 체크아웃 시간
     * @return true/false
     */
    boolean isRested(Long roomsId, LocalDateTime checkIn, LocalDateTime checkOut);

    /**
     * 예약 정보중 체크인 시간과 체크 아웃 시간을 수정한다
     * @Param 유저 Id
     * @Param 방 Id
     * @Param 해당 예약 Id
     * @Param 수정 시간
     * @return true/false
     */
    ReservationResponseDto.UpdateReservationResponseDto updateReservation(Long userId,
                                                                 Long roomsId,
                                                                 Long reservationId,
                                                                 ReservationRequestDto.UpdateReservationDto requestDto);

    /**
     * 해당 예약 정보를 조회한다.
     * @Param 유저 Id
     * @Param 해당 예약 Id
     * @return 예약 정보
     */
    ReservationResponseDto.ReadReservationResponseDto readReservation(Long userId, Long reservationId);

    /**
     * 유저의 예약 목록을 조회한다.
     * @Param 유저 Id
     * @return 예약 정보 목록
     */
    List<ReservationResponseDto.ReadReservationResponseDto> readReservationList(Long id);

    /**
     * 해당 예약을 삭제한다.
     * @Param 유저 Id
     * @Param 예약 Id
     * @return void
     */
    void deleteReservation(Long userId, Long reservationId);
}

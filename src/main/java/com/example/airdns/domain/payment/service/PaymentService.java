package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    /**
     * @param reservationId 예약 ID
     * @param requestDto 결제 요청 DTO
     */
    PaymentResponseDto.CreatePaymentResponseDto createPayment(Long userId,
            Long reservationId,
            PaymentRequestDto.CreatePaymentRequestDto requestDto);

    /**
     * @param reservationId 예약 ID
     * @param paymentId 결제 ID
     */
    PaymentResponseDto.ReadPaymentResponseDto readPayment(Long reservationId, Long paymentId);

    /**
     * 유저 아이디를 통한 결제 아이디 리스트 조회
     * @param userId 유저 ID
     * @return 유저 아이디를 통한 결제 아이디 리스트
     */
    List<Long> findPaymentIdsByUserId(Long userId);

    /**
     * 결제 아이디를 통한 결제 정보 소프트 삭제
     * @param paymentId 결제 ID
     */
    void saveDeletedPaymentInfo(Long paymentId);

    /**
     * 유저 아이디를 통한 결제 정보 하드 삭제
     * @param userId 유저 ID
     */
    void deleteByUserId(Long userId);

    /**
     * 예약 아이디 리스트를 통해 결제 아이디 리스트 조회
     * @param reservationIds 예약 아이디 리스트
     * @return 예약 아이디 리스트를 통해 결제 아이디 리스트
     */
    List<Long> findPaymentIdsByReservationIds(List<Long> reservationIds);

    /**
     * 방 아이디를 통해 결제 정보 하드 삭제
     * @param roomId 방 ID
     */
    void deleteByRoomId(Long roomId);

    /**
     * 예약 아이디를 통한 결제 정보 하드 삭제
     * @param reservationId 예약 ID
     */
    void deleteByReservationId(Long reservationId);

    /**
     * 예약 아이디를 통한 결제 아이디 리스트 조회
     * @param reservationId 예약 ID
     * @return 예약 아이디를 통한 결제 아이디 리스트
     */
    List<Long> findPaymentIdsByReservationId(Long reservationId);

    /**
     * 삭제 시간을 통해 결제 정보 하드 삭제 및 아카이빙
     * @param deleteTime 삭제 시간
     */
    void deletePayment(LocalDateTime deleteTime);
}

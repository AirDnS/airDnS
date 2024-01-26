package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto.ReadPaymentResponseDto;

public interface PaymentService {
    /**
     * @param reservationId 예약 ID
     * @param requestDto 결제 요청 DTO
     */
    PaymentResponseDto.CreatePaymentResponseDto createPayment(Long reservationId,
            Long userId,
            PaymentRequestDto.CreatePaymentRequestDto requestDto);

    PaymentResponseDto.ReadPaymentResponseDto readPayment(Long reservationId, Long paymentId);
}

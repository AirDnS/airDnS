package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;

public interface PaymentService {
    /**
     * @param reservationId 예약 ID
     * @param requestDto 결제 요청 DTO
     */
    PaymentResponseDto.CreatePaymentResponseDto createPayment(Long reservationId,
            Long userId,
            PaymentRequestDto.CreatePaymentRequestDto requestDto);

}

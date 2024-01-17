package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentRequestDto.RequestPaymentDto;
import java.util.NoSuchElementException;

public interface PaymentService {
    /**
     * 결제 요청을 처리하는 메서드
     * @param reservationId 예약 ID
     * @param requestDto 결제 요청 DTO
     */

    String requestPayment(Long reservationId, PaymentRequestDto.RequestPaymentDto requestDto);
}
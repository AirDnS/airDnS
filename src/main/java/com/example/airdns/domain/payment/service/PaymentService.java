package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentRequestDto.RequestPaymentDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import java.util.NoSuchElementException;

public interface PaymentService {
    /**
     * @param reservationId 예약 ID
     * @param requestDto 결제 요청 DTO
     */
    PaymentResponseDto requestPayment(Long reservationId, PaymentRequestDto.RequestPaymentDto requestDto);


}

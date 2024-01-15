package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto.ReadPaymentResponseDto getPaymentResult(String authorization, Long orderId, Long amount, Long paymentKey);
}

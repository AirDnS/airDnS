package com.example.airdns.domain.payment.controller;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImplV1 paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<CommonResponse<PaymentResponseDto>> requestPayment(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PaymentRequestDto.RequestPaymentDto requestDto) {

        log.info("Received payment confirmation request: paymentKey={}, orderId={}, amount={}",
                requestDto.getPaymentKey(), requestDto.getOrderId(), requestDto.getAmount());
        PaymentResponseDto paymentResponseDto = paymentService.requestPayment(requestDto);
        if (paymentResponseDto != null) {
            return ResponseEntity.ok(new CommonResponse<>(paymentResponseDto));
        } else {
            return ResponseEntity.ok(new CommonResponse<>(null));
        }

    }
}
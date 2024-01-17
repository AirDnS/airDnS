// PaymentController.java
package com.example.airdns.domain.payment.controller;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImplV1 paymentService;

    @PostMapping("/request")
    public ResponseEntity<CommonResponse<String>> requestPayment(
            @RequestBody PaymentRequestDto.RequestPaymentDto requestDto) {
        Long reservationId = requestDto.getReservationId();
        String paymentKey = paymentService.requestPayment(reservationId, requestDto);
        return ResponseEntity.ok(new CommonResponse<String>(paymentKey)); //client
    }
}

package com.example.airdns.domain.payment.controller;

import com.example.airdns.domain.payment.dto.PaymentReqDto;
import com.example.airdns.domain.payment.dto.PaymentResDto;
import com.example.airdns.domain.payment.service.TossPaymentServiceImplV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final TossPaymentServiceImplV1 tossPaymentService;

    @Autowired
    public PaymentController(TossPaymentServiceImplV1 tossPaymentService) {
        this.tossPaymentService = tossPaymentService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> processPayment(@RequestBody PaymentReqDto paymentReqDto) {
        PaymentResDto responseDto = tossPaymentService.processPayment(paymentReqDto)
                .toPaymentResDto();
        return ResponseEntity.ok(responseDto);
    }

}
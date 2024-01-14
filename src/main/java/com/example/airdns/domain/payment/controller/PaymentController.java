package com.example.airdns.domain.payment.controller;

import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.service.PaymentService;
import com.example.airdns.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<CommonResponse<?>> getPaymentResult(
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey){

        // 해당 secretKey와 encoder 작업을 JwtUtil에서 실행할지? @PostConstruct
        // 인가 코드 만드는거라... 이거는 그냥 jwtUitl에서 수행하는게 맞는듯?
        String secretKey = "test_ak_ZORzdMaqN3wQd5k6ygr5AkYXQGwy:";

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(secretKey.getBytes(StandardCharsets.UTF_8));
        String authorization = "Basic" + new String(encodedBytes, 0, encodedBytes.length);

        PaymentResponseDto.ReadPaymentResponseDto responseDto = null;// paymentService.getPaymentResult(authorization);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "룸 좋아요 취소 성공", responseDto)
        );
    }
}

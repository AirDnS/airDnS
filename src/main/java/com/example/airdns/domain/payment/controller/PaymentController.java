package com.example.airdns.domain.payment.controller;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "payment", description = "Payment API")
public class PaymentController {

    private final PaymentServiceImplV1 paymentService;

    @PostMapping("{/reservation/{reservationId}/payments/confirm")
    @Operation(summary = "결제 요청", description = "예약 건에 대해 결제 요청을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "결제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 예약이 없습니다."),
    })
    public ResponseEntity<CommonResponse<PaymentResponseDto>> requestPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reservationId,
            @RequestBody PaymentRequestDto.RequestPaymentDto requestDto) {

        log.info("Received payment confirmation request: paymentKey={}, orderId={}, amount={}",
                requestDto.getPaymentKey(), requestDto.getOrderId(), requestDto.getAmount());
        PaymentResponseDto paymentResponseDto = paymentService.requestPayment(reservationId,
                requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse<>(
                        HttpStatus.CREATED,
                        "결제 성공"
                )
        );

    }

}
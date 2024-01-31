package com.example.airdns.domain.payment.controller;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto.ReadPaymentResponseDto;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("reservation/{reservationId}/payment")
    @Operation(summary = "결제 요청", description = "예약 건에 대해 결제 요청을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "결제 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 예약입니다."),
    })
    public ResponseEntity<CommonResponse<PaymentResponseDto.CreatePaymentResponseDto>> createPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reservationId,
            @RequestBody PaymentRequestDto.CreatePaymentRequestDto requestDto) {

        PaymentResponseDto.CreatePaymentResponseDto paymentResponseDto = paymentService.createPayment(
                userDetails.getUser().getId(),
                reservationId,
                requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse<>(
                        HttpStatus.CREATED,
                        "결제 성공",
                        paymentResponseDto
                )
        );

    }

    @GetMapping("/reservation/{reservationId}/payment/{paymentId}")
    @Operation(summary = "결제 단건 조회", description = "해당 결제를 조회한다.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "결제내역 조회에 성공!",
            content = {
                    @Content(schema = @Schema(implementation = ReadPaymentResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "결제내역이 존재하지 않습니다."),
    })
    public ResponseEntity<CommonResponse> readPayment(
            @PathVariable Long reservationId,
            @PathVariable Long paymentId) {
        PaymentResponseDto.ReadPaymentResponseDto paymentResponseDto = paymentService.readPayment(
                reservationId, paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse(HttpStatus.OK,
                        "결제 조회 성공!",
                        paymentResponseDto)
        );
    }
}

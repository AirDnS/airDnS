package com.example.airdns.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PaymentResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "결제 조회 응답 dto")
    public static class ReadPaymentResponseDto {
        @Schema(description = "좋아요 조회 응답 내용", defaultValue = "get like response")
        private String nickName;
    }
}

package com.example.airdns.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Create Payment request")
    public static class CreatePaymentRequestDto implements Serializable {
        @Schema(description = "결제 수단", example = "CARD")
        private String paymentType;

        @Schema(description = "결제 고유 값", example = "1qwerksldlfkalskdf")
        private String paymentKey;

        @Schema(description = "결제 주문 id", example = "sdfqwefaasd1as")
        private String orderId;

        @Schema(description = "결제 id", example = "1")
        private Long id;

        @Schema(description = "결제 금액", example = "10000")
        private Long amount;

        @Schema(description = "결제 이름", example = "아름다운 방")
        private String orderName;

        @Schema(description = "예약 번호", example = "1")
        private Long reservationId;
    }
}

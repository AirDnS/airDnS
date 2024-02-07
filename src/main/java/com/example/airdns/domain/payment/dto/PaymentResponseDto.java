package com.example.airdns.domain.payment.dto;

import com.example.airdns.domain.payment.entity.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PaymentResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Create Payment Response")
    public static class CreatePaymentResponseDto implements Serializable {

        @Schema(description = "결제 id", example = "1")
        private Long id;

        @Schema(description = "결제 수단", example = "CARD")
        private String paymentType;

        @Schema(description = "결제 금액", example = "10000")
        private Long amount;

        @Schema(description = "결제 이름", example = "아름다운 방")
        private String orderName;

        @Schema(description = "예약 번호", example = "1")
        private Long reservationId;

        public static CreatePaymentResponseDto from(Payment payment) {
            return CreatePaymentResponseDto.builder()
                    .id(payment.getId())
                    .paymentType(payment.getPaymentType())
                    .amount(payment.getAmount())
                    .orderName(payment.getOrderName())
                    .reservationId(payment.getReservation().getId())
                    .build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Read Payment Response")
    public static class ReadPaymentResponseDto implements Serializable {


        @Schema(description = "결제 id", example = "1")
        private Long id;

        @Schema(description = "결제 수단", example = "CARD")
        private String paymentType;

        @Schema(description = "결제 금액", example = "10000")
        private Long amount;

        @Schema(description = "결제 이름", example = "아름다운 방")
        private String orderName;

        @Schema(description = "예약 번호", example = "1")
        private Long reservationId;
        public static ReadPaymentResponseDto from(Payment payment) {
            return ReadPaymentResponseDto.builder()
                    .id(payment.getId())
                    .paymentType(payment.getPaymentType())
                    .amount(payment.getAmount())
                    .orderName(payment.getOrderName())
                    .reservationId(payment.getReservation().getId())
                    .build();
        }


    }
}
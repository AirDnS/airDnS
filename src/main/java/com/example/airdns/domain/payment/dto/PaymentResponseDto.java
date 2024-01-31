package com.example.airdns.domain.payment.dto;

import com.example.airdns.domain.payment.entity.Payment;
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
    public static class CreatePaymentResponseDto implements Serializable {

        private Long id;
        private String paymentType;
        private Long amount;
        private String orderName;
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
    public static class ReadPaymentResponseDto implements Serializable {

        private Long id;
        private String paymentType;
        private Long amount;
        private String orderName;
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
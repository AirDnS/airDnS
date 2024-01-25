package com.example.airdns.domain.payment.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PaymentRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePaymentRequestDto implements Serializable {

        private String paymentType;
        private String paymentKey;
        private String orderId;
        private Long id;
        private Long amount;
        private String orderName;
        private Long reservationId;
    }
}

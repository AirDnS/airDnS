package com.example.airdns.domain.payment.dto;

import com.example.airdns.domain.payment.entity.Payments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    private String paymentType;
    private Long amount;
    private String orderName;
    private String orderId;
    private String paymentKey;
    private Boolean isCanceled;
    private Boolean isPaySuccess;
    private String failReason;
    private String cancelReason;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestPaymentDto {

        private String paymentType;
        private Long amount;
        private String orderId;
        private Long reservationId;
        private String paymentKey;
        private String payName;
        public Payments toEntity() {
            return Payments.builder()
                    .paymentType(paymentType)
                    .amount(amount)
                    .orderId(orderId)
                    .paymentKey(paymentKey)
                    .build();
        }
    }
}
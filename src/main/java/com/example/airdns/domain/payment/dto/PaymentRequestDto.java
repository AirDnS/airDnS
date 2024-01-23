package com.example.airdns.domain.payment.dto;

import com.example.airdns.domain.payment.entity.Payments;
import com.example.airdns.domain.reservation.entity.Reservation;
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
    private Long reservationId;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestPaymentDto {

        private String paymentType;
        private Long amount;
        private String orderId;
        private String paymentKey;
        private String payName;
        private Reservation reservation;

        public Payments toEntity() {
            return Payments.builder()
                    .paymentType(paymentType)
                    .amount(amount)
                    .orderId(orderId)
                    .paymentKey(paymentKey)
                    .reservation(reservation)
                    .build();
        }

        public Reservation getReservationId() {
            return getReservation();
        }
    }
}

package com.example.airdns.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {

    private String payType;
    private Long amount;
    private String orderId;
    private String customerEmail;
    private String successUrl;
    private String failUrl;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;
    private String createdAt;
    private String paymentKey;
    private Long reservationId;


}
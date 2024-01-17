package com.example.airdns.domain.payment.dto;

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
public class PaymentResponseDto {

    private String payType; // 결제 타입 - 카드/현금/포인트
    private Long amount; // 가격 정보
    private String orderId; // 주문 Id
    private String customerEmail; // 고객 이메일
    private String successUrl; // 성공 시 리다이렉트 될 URL
    private String failUrl; // 실패 시 리다이렉트 될 URL
    private String failReason; // 실패 이유
    private boolean cancelYN; // 취소 YN
    private String cancelReason; // 취소 이유
    private String createdAt; // 결제가 이루어진 시간


    // DB에 저장될 결제 관련 정보
    public PaymentResponseDto toPaymentResponseDto() {
        return PaymentResponseDto.builder()
                .payType(payType)
                .amount(amount)
                .orderId(orderId)
                .createdAt(String.valueOf(getCreatedAt()))
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }
}

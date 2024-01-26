package com.example.airdns.domain.payment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentExceptionCode {

    /* 404 - NOT FOUND */
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "PAYMENT-001", "해당 결제 정보가 존재하지 않습니다."),
    NOT_FOUND_MATCHED_PAYMENT(HttpStatus.NOT_FOUND, "PAYMENT-002", "해당 결제 정보와 일치하는 결제 내역이 존재하지 않습니다."),
    NOT_FOUND_MATCHED_RESERVATION(HttpStatus.NOT_FOUND, "PAYMENT-003", "예약번호와 일치하는 결제 내역이 존재하지 않습니다."),

    FORBIDDEN_RESERVATION_NOT_USER(HttpStatus.FORBIDDEN, "USER-001", "해당 사용자가 예약한 것이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

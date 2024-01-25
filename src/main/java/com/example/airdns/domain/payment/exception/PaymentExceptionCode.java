package com.example.airdns.domain.payment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentExceptionCode {

    /* 404 - NOT FOUND */
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "Payment-001", "해당 결제 정보가 존재하지 않습니다."),
    NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND, "Reservation-001", "해당 예약 정보가 존재하지 않습니다."),
    NOT_FOUND_MATCHED_RESERVATION(HttpStatus.NOT_FOUND, "Reservation-001", "해당 예약 정보가 존재하지 않습니다."),

    FORBIDDEN_RESERVATION_NOT_USER(HttpStatus.FORBIDDEN, "PAYMENT-001", "해당 사용자가 예약한 것이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

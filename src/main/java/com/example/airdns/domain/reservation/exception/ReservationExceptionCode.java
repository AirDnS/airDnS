package com.example.airdns.domain.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReservationExceptionCode {

    /* 404 - NOT FOUND */
    NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND, "Reservation-001", "해당 예약 정보가 존재하지 않습니다."),

    /* 400 - BAD REQUEST */
    BAD_REQUEST_RESERVATION(HttpStatus.BAD_REQUEST, "Reservation-002", "예약 정보가 잘못 입력 되었습니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

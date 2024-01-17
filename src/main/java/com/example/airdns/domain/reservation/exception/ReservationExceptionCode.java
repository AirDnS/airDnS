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
    BAD_REQUEST_RESERVATION_REQUEST(HttpStatus.BAD_REQUEST, "Reservation-002", "예약 정보가 잘못 입력 되었습니다."),

    BAD_REQUEST_RESERVATION_NOT_RESERVE(HttpStatus.BAD_REQUEST, "Reservation-003", "해당 시간에 예약 할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

package com.example.airdns.domain.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReservationExceptionCode {

    /* 404 - NOT FOUND */
    NOT_FOUND_RESERVATION(
            HttpStatus.NOT_FOUND,
            "Reservation-001",
            "해당 예약 정보가 존재하지 않습니다."),

    /* 400 - BAD REQUEST */
    BAD_REQUEST_RESERVATION_CHECK_IN_IS_BEFORE_NOW(
            HttpStatus.BAD_REQUEST,
            "Reservation-002",
            "체크인 시간이 과거로 입력했습니다."),

    BAD_REQUEST_RESERVATION_CHECK_IN_IS_AFTER_CHECK_OUT(
            HttpStatus.BAD_REQUEST,
            "Reservation-003",
            "체크 인 시간이 체크 아웃 시간보다 미래에 있습니다."),

    BAD_REQUEST_RESERVATION_CHECK_IN_IS_SAME_CHECK_OUT(
            HttpStatus.BAD_REQUEST,
            "Reservation-004",
            "체크 인 시간과 체크 아웃 시간이 같습니다"),

    BAD_REQUEST_RESERVATION_NOT_RESERVE(
            HttpStatus.BAD_REQUEST,
            "Reservation-005",
            "해당 시간에 이미 예약이 있습니다."),

    /* 423 - Lock */

    LOCKED_REDISSON(
            HttpStatus.LOCKED,
            "Reservation-006",
            "Lock 획득 실패");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

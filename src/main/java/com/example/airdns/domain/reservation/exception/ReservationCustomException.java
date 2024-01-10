package com.example.airdns.domain.reservation.exception;

import com.example.airdns.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ReservationCustomException extends CustomException {
    public ReservationCustomException(ReservationExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());
    }
}

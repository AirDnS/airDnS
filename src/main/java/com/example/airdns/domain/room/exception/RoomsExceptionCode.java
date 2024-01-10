package com.example.airdns.domain.room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomsExceptionCode {

    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
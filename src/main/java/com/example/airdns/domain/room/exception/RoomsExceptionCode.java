package com.example.airdns.domain.room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomsExceptionCode {

    INVALID_ROOMS_ID(HttpStatus.BAD_REQUEST, "ROOM-001", "방 번호가 유효하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
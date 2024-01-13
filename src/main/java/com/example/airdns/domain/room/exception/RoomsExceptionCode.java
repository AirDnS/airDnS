package com.example.airdns.domain.room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomsExceptionCode {

    NO_PERMISSION_USER(HttpStatus.UNAUTHORIZED, "ROOM-001", "권한이 없는 유저입니다."),
    INVALID_ROOMS_ID(HttpStatus.BAD_REQUEST, "ROOM-003", "방 번호가 유효하지 않습니다."),
;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
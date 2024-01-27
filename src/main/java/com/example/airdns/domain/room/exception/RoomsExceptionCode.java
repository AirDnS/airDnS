package com.example.airdns.domain.room.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoomsExceptionCode {

    NO_PERMISSION_USER(HttpStatus.FORBIDDEN, "ROOM-001", "권한이 없는 유저입니다."),
    INVALID_ROOMS_ID(HttpStatus.BAD_REQUEST, "ROOM-002", "방 번호가 유효하지 않습니다."),
    IMAGES_NOT_EXIST(HttpStatus.BAD_REQUEST, "ROOM-003", "이미지 정보가 없습니다." ),
    EXIST_RESERVATION(HttpStatus.BAD_REQUEST, "ROOM-004", "해당 시간에 예약이 있습니다." ),
    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
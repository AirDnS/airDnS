package com.example.airdns.domain.image.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImagesExceptionCode {

    NO_PERMISSION_USER(HttpStatus.UNAUTHORIZED, "IMAGE-001", "권한이 없는 유저입니다."),
    INVALID_IMAGES_ID(HttpStatus.BAD_REQUEST, "IMAGE-003", "이미지 번호가 유효하지 않습니다."),

    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
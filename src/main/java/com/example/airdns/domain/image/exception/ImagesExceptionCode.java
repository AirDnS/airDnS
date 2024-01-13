package com.example.airdns.domain.image.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImagesExceptionCode {

    NO_PERMISSION_USER_IMAGES(HttpStatus.UNAUTHORIZED, "IMAGE-001", "이미지에 대한 권한이 없습니다."),
    INVALID_IMAGES_ID(HttpStatus.BAD_REQUEST, "IMAGE-002", "이미지 번호가 유효하지 않습니다."),

    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
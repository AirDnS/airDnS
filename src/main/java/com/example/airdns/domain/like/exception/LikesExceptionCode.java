package com.example.airdns.domain.like.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikesExceptionCode{
    USER_NOT_LIKED(HttpStatus.BAD_REQUEST, "LIKES-001", "해당 사용자가 좋아요를 누르지 않았습니다."),
    ALREDAY_EXIST_LIKES(HttpStatus.BAD_REQUEST, "LIKES-002", "해당 사용자는 좋아요를 이미 눌렀습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

package com.example.airdns.domain.like.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikesExceptionCode {
    DUPLICATE_LIKE(HttpStatus.BAD_REQUEST, "LIKES-001", "해당 사용자는 해당 룸에 '좋아요'를 눌렀습니다."),
    USER_NOT_LIKED(HttpStatus.BAD_REQUEST, "LIKES-002", "해당 사용자는 해당 룸에 '좋아요'를 누르지 않았습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

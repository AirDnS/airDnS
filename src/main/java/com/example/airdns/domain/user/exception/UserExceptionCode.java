package com.example.airdns.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"USER-001", "유효하지 않은 토큰 입니다."),
    SUCCESS_LOGIN(HttpStatus.OK,"USER-002","로그인 성공"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER-003", "해당 유저는 존재하지 않습니다."),
    NOT_EQUALS_PASSWORD(HttpStatus.NOT_FOUND,"USER-004", "현재 비밀번호와 일치하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

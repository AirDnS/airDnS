package com.example.airdns.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UsersExceptionCode {
    // BAD_REQUEST 400
    BAD_REQUEST_NOT_MATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "USER-012", "이메일 인증번호가 일치하지 않습니다"),

    // UNAUTHORIZED 401
    UNAUTHORIZED_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "USER-002", "토큰의 유효기간이 만료 되었습니다"),

    UNAUTHORIZED_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "USER-003", "유효하지 않은 토큰 입니다"),

    UNAUTHORIZED_TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "USER-004", "지원되지 않는 토큰 입니다"),

    UNAUTHORIZED_TOKEN_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "USER-005", "토큰의 서명이 유효하지 않습니다"),

    UNAUTHORIZED_TOKEN_EMPTY_CLAIMS(HttpStatus.UNAUTHORIZED, "USER-006", "잘못된 토큰 입니다"),

    UNAUTHORIZED_TOKEN_GENERIC_ERROR(HttpStatus.UNAUTHORIZED, "USER-007", "토큰 처리 중 오류가 발생했습니다"),

    UNAUTHORIZED_TOKEN_IS_NULL(HttpStatus.UNAUTHORIZED, "USER-008", "토큰이 없습니다"),

    // FORRBIDDEN 403
    FORBIDDEN_YOUR_NOT_COME_IN(HttpStatus.FORBIDDEN, "USER-009", "접근 권한이 없습니다"),

    // NOT_FOUND 404
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER-010", "해당 유저는 존재하지 않습니다"),

    NOT_FOUND_AUTH_CODE(HttpStatus.NOT_FOUND, "USER-013", "이메일 인증번호가 존재하지 않습니다"),

    // CONFLICT 409
    CONFLICT_USER_EMAIL_IN_USE(HttpStatus.CONFLICT, "USER-011", "사용자 이메일이 이미 사용 중 입니다");
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

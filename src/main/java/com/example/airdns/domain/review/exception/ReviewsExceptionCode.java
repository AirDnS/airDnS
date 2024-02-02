package com.example.airdns.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewsExceptionCode {
    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST, "REVIEWS-001", "해당 방에 대한 리뷰가 존재하지 않습니다."),
    ALREADY_EXISTS_REVIEW(HttpStatus.BAD_REQUEST, "REVIEWS-002", "해당 방에 대한 리뷰가 이미 존재합니다."),
    NOT_MODIFY_REVIEW(HttpStatus.FORBIDDEN, "REVIEWS-003", "해당 방에 대한 리뷰를 수정할 권한이 없습니다."),
    NOT_DELETE_REVIEW(HttpStatus.FORBIDDEN, "REVIEWS-004", "해당 방에 대한 리뷰를 삭제할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

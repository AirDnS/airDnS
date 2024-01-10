package com.example.airdns.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewsExceptionCode {
    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST, "REVIEWS-001", "해당 방에 대한 리뷰가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

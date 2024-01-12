package com.example.airdns.domain.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ReviewsException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
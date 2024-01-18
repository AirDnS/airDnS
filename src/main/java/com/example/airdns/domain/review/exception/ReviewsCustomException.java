package com.example.airdns.domain.review.exception;

import com.example.airdns.global.exception.CustomException;

public class ReviewsCustomException extends CustomException {
    public ReviewsCustomException(ReviewsExceptionCode exceptionCode){
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());
    }
}
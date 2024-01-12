package com.example.airdns.domain.review.exception;

public class NotModifyReviewException extends ReviewsException{

    public NotModifyReviewException(ReviewsExceptionCode e){
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

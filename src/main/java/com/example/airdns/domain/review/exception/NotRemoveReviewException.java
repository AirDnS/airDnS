package com.example.airdns.domain.review.exception;

public class NotRemoveReviewException extends ReviewsException{

    public NotRemoveReviewException(ReviewsExceptionCode e){
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

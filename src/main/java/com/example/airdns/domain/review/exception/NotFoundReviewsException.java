package com.example.airdns.domain.review.exception;

import lombok.Getter;

@Getter
public class NotFoundReviewsException extends ReviewsException {

    public NotFoundReviewsException(ReviewsExceptionCode e){
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

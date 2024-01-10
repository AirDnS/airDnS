package com.example.airdns.domain.review.exception;

import lombok.Getter;

@Getter
public class ReviewAlreadyExistsException extends ReviewsException{

    public ReviewAlreadyExistsException(ReviewsExceptionCode e){
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

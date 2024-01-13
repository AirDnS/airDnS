package com.example.airdns.domain.like.exception;

import lombok.Getter;

@Getter
public class UserNotLikedException extends LikesException {

    public UserNotLikedException(LikesExceptionCode e){
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

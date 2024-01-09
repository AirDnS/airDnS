package com.example.airdns.domain.like.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;

@Getter
public class DuplicateLikeException extends CustomException {
    public DuplicateLikeException(LikesExceptionCode e){
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

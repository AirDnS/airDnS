package com.example.airdns.domain.like.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class LikesCustomException extends CustomException {

    public LikesCustomException(LikesExceptionCode exceptionCode){
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());
    }
}
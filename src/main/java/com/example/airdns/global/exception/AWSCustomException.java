package com.example.airdns.global.exception;

import lombok.Getter;

@Getter
public class AWSCustomException extends CustomException {
    public AWSCustomException(GlobalExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

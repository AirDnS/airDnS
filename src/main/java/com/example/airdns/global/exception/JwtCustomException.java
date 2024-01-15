package com.example.airdns.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtCustomException extends CustomException {
    public JwtCustomException(GlobalExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

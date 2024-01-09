package com.example.airdns.domain.user.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;

@Getter
public class UserCustomException extends CustomException {
    public UserCustomException(UserExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

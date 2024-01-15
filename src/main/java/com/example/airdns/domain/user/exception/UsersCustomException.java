package com.example.airdns.domain.user.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;

@Getter
public class UsersCustomException extends CustomException {
    public UsersCustomException(UsersExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

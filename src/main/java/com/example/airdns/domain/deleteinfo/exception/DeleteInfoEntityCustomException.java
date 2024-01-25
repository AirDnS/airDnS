package com.example.airdns.domain.deleteinfo.exception;

import com.example.airdns.global.exception.CustomException;

public class DeleteInfoEntityCustomException extends CustomException {
    public DeleteInfoEntityCustomException(DeleteInfoEntityExceptionCode exceptionCode) {
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());
    }
}

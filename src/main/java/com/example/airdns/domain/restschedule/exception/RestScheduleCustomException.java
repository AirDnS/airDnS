package com.example.airdns.domain.restschedule.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;

@Getter
public class RestScheduleCustomException extends CustomException {
    public RestScheduleCustomException(RestScheduleExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

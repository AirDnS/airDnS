package com.example.airdns.domain.room.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoomsCustomException extends CustomException {
    public RoomsCustomException(RoomsExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

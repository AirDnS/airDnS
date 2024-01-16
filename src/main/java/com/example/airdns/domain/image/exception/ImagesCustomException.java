package com.example.airdns.domain.image.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;

@Getter
public class ImagesCustomException extends CustomException {
    public ImagesCustomException(ImagesExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

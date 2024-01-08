package com.example.airdns.global.advice;

import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException customException){
        log.error("CustomException ", customException);
        return ResponseEntity.status(customException.getHttpStatus()).body(
                new CommonResponse<String>(customException.getHttpStatus(), customException.getMessage(), customException.getErrorCode())
        );
    }
}

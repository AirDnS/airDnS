package com.example.airdns.domain.like.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class LikesException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
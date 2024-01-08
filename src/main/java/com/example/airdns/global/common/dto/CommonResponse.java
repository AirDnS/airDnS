package com.example.airdns.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    private T data;
}

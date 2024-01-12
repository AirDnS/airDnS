package com.example.airdns.global.common.dto;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {

    private HttpStatus httpStatus;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CommonResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }
}

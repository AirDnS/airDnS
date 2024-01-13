package com.example.airdns.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    //TODO HttpStatus값이 value로 나가도록 수정할 것
    public CommonResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }
}

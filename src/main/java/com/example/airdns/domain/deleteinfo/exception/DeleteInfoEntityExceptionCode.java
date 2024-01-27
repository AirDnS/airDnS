package com.example.airdns.domain.deleteinfo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DeleteInfoEntityExceptionCode {

    NOT_FOUND_ENTITY(HttpStatus.BAD_REQUEST, "DeleteInfo-001", "해당하는 엔티티가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}

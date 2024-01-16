package com.example.airdns.domain.equipment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EquipmentsExceptionCode {

    INVALID_EQUIPMENTS_ID(HttpStatus.BAD_REQUEST, "EQUIPMENT-001", "장비 번호가 올바르지 않습니다."),

    ;


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}

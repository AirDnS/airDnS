package com.example.airdns.domain.equipment.exception;

import com.example.airdns.global.exception.CustomException;
import lombok.Getter;

@Getter
public class EquipmentsCustomException extends CustomException {
    public EquipmentsCustomException(EquipmentsExceptionCode e) {
        super(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    }
}

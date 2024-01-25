package com.example.airdns.domain.payment.exception;

import com.example.airdns.global.exception.CustomException;

public class PaymentCustomException extends CustomException {

    public PaymentCustomException(PaymentExceptionCode exceptionCode){
        super(exceptionCode.getHttpStatus(), exceptionCode.getErrorCode(), exceptionCode.getMessage());
    }
}

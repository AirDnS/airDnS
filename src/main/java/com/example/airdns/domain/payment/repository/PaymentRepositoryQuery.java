package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.QPayment;

import java.util.List;

public interface PaymentRepositoryQuery {
    List<Long> findDeletedPaymentIds(QPayment qPayment, Long userId);
    void deleteByUserId(QPayment qPayment, Long userId);
}

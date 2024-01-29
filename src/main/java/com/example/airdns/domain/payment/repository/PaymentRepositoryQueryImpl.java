package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PaymentRepositoryQueryImpl implements PaymentRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Long> findDeletedPaymentIds(QPayment qPayment, Long userId){
        return jpaQueryFactory.select(qPayment.id)
                .from(qPayment)
                .where(qPayment.reservation.users.id.eq(userId))
                .fetch();
    }

    @Override
    public void deleteByUserId(QPayment qPayment, Long userId){
        jpaQueryFactory.delete(qPayment)
                .where(qPayment.reservation.users.id.eq(userId))
                .execute();
    }
}

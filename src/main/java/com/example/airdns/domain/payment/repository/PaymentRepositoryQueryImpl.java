package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryQueryImpl implements PaymentRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;
    QPayment qPayment = QPayment.payment;
    @Override
    public List<Long> findPaymentIdsByUserId(Long userId){
        return jpaQueryFactory.select(qPayment.id)
                .from(qPayment)
                .where(qPayment.reservation.users.id.eq(userId))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId){
        jpaQueryFactory.delete(qPayment)
                .where(qPayment.reservation.users.id.eq(userId))
                .execute();
    }

    @Override
    public List<Long> findPaymentIdsByReservationIds(List<Long> reservationIds){
        return reservationIds.stream()
                .flatMap(reservationId -> jpaQueryFactory.select(qPayment.id)
                        .from(qPayment)
                        .where(qPayment.reservation.id.eq(reservationId))
                        .fetch().stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findPaymentIdsByReservationId(Long reservationId){
        return jpaQueryFactory.select(qPayment.id)
                .from(qPayment)
                .where(qPayment.reservation.id.eq(reservationId))
                .fetch();
    }

    @Override
    public void deleteByRoomId(Long roomId){
        jpaQueryFactory.delete(qPayment)
                .where(qPayment.reservation.rooms.id.eq(roomId))
                .execute();
    }

    @Override
    @Transactional
    public void deleteByReservationId(Long reservationId){
        jpaQueryFactory.delete(qPayment)
                .where(qPayment.reservation.id.eq(reservationId))
                .execute();
    }

    @Override
    public List<Long> findPaymentIdsByDeleteTime(LocalDateTime deleteTime){
        return jpaQueryFactory.select(qPayment.id)
                .from(qPayment)
                .where(qPayment.isDeleted.eq(true)
                        .and(qPayment.deletedAt.before(deleteTime)))
                .fetch();
    }

    @Override
    @Transactional
    public void deletePaymentId(Long paymentId){
        jpaQueryFactory.delete(qPayment)
                .where(qPayment.id.eq(paymentId))
                .execute();
    }
}

package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.QRooms;
import com.example.airdns.domain.room.entity.Rooms;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoryQueryImpl extends QuerydslRepositorySupport implements ReservationRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    public ReservationRepositoryQueryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Reservation.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Long> findDeletedReservationIds(QReservation qReservation, Long userId){
        return jpaQueryFactory.select(qReservation.id)
                .from(qReservation)
                .where(qReservation.users.id.eq(userId))
                .fetch();
    }

    @Override
    public void deleteByUserId(QReservation qReservation, Long userId){
        jpaQueryFactory.delete(qReservation)
                .where(qReservation.users.id.eq(userId))
                .execute();
    }
}

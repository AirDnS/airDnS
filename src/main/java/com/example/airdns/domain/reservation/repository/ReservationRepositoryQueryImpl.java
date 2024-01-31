package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.QRooms;
import com.example.airdns.domain.room.entity.Rooms;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationRepositoryQueryImpl extends QuerydslRepositorySupport implements ReservationRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;
    QReservation qReservation = QReservation.reservation;
    public ReservationRepositoryQueryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Reservation.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Long> findReservationIdsByUserId(Long userId){
        return jpaQueryFactory.select(qReservation.id)
                .from(qReservation)
                .where(qReservation.users.id.eq(userId))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId){
        jpaQueryFactory.delete(qReservation)
                .where(qReservation.users.id.eq(userId))
                .execute();
    }

    @Override
    public List<Long> findReservationIdsByRoomId(Long roomId){
        return jpaQueryFactory.select(qReservation.id)
                .from(qReservation)
                .where(qReservation.rooms.id.eq(roomId))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteByRoomId(Long roomId){
        jpaQueryFactory.delete(qReservation)
                .where(qReservation.rooms.id.eq(roomId))
                .execute();
    }

    @Override
    public List<Long> findReservationIds(LocalDateTime deleteTime){
        return jpaQueryFactory.select(qReservation.id)
                .from(qReservation)
                .where(qReservation.isCancelled.isTrue()
                        .and(qReservation.canceledAt.before(deleteTime))
                        .or(qReservation.checkOut.before(deleteTime).and(qReservation.canceledAt.isNull())))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteReservationInfo(Long reservationId){
        jpaQueryFactory.delete(qReservation)
                .where(qReservation.id.eq(reservationId))
                .execute();
    }
}

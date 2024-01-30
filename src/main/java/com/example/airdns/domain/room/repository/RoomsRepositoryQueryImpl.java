package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.converter.RoomsConverter;
import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.airdns.domain.room.entity.QRooms.rooms;
import static com.example.airdns.domain.roomequipment.entity.QRoomEquipments.roomEquipments;

public class RoomsRepositoryQueryImpl extends QuerydslRepositorySupport implements RoomsRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    public RoomsRepositoryQueryImpl(JPAQueryFactory queryFactory) {
        super(Rooms.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<RoomsResponseDto.ReadRoomsResponseDto> findAllSearchFilter(
            Pageable pageable, RoomsSearchConditionDto condition) {
        JPQLQuery<Rooms> query = queryFactory
                .select(rooms)
                .from(rooms)
                .where(
                        eqName(condition.getKeyword())
                                .or(eqAddress(condition.getKeyword())),
                        betweenPrice(condition.getStartPrice(), condition.getEndPrice()),
                        betweenSize(condition.getStartSize(), condition.getEndSize()),
                        inEquipment(condition.getEqupmentList()),
                        rooms.isDeleted.isFalse(),
                        rooms.isClosed.isFalse()
                );

        return getRoomsResponseDto(pageable, query);
    }

    @Override
    public Page<RoomsResponseDto.ReadRoomsResponseDto> findAllByHost(
            Pageable pageable, RoomsSearchConditionDto condition) {
        JPQLQuery<Rooms> query = queryFactory
                .select(rooms)
                .from(rooms)
                .where(
                        eqName(condition.getKeyword()),
                        rooms.users.eq(condition.getUsers()),
                        rooms.isDeleted.isFalse()
                );

        return getRoomsResponseDto(pageable, query);
    }

    private Page<RoomsResponseDto.ReadRoomsResponseDto> getRoomsResponseDto(Pageable pageable, JPQLQuery<Rooms> query) {
        List<RoomsResponseDto.ReadRoomsResponseDto> content;
        try (Stream<Rooms> stream = Objects.requireNonNull(this.getQuerydsl())
                .applyPagination(pageable, query)
                .stream()) {
            content = stream.map(RoomsConverter::toDto).toList();
        }

        return new PageImpl<>(content, pageable, query.fetchCount());
    }

    private BooleanBuilder eqName(String keyword) {
        return new BooleanBuilder(
                keyword == null || keyword.isEmpty()
                        ? null
                        : rooms.name.like("%" + keyword + "%"));
    }

    private BooleanBuilder eqAddress(String keyword) {
        return new BooleanBuilder(
                keyword == null || keyword.isEmpty()
                        ? null
                        : rooms.address.like("%" + keyword + "%"));
    }

    private BooleanExpression betweenPrice(BigDecimal startPrice, BigDecimal endPrice) {
        if (startPrice != null && endPrice != null) {
            return rooms.price.between(startPrice, endPrice);
        }
        if (startPrice != null) {
            return rooms.price.goe(startPrice); // price >= startPirce
        }
        if (endPrice != null) {
            return rooms.price.loe(endPrice); // price <= endPirce
        }

        return null;
    }

    private BooleanExpression betweenSize(Integer startSize, Integer endSize) {
        if (startSize != null && endSize != null) {
            return rooms.size.between(startSize, endSize);
        }
        if (startSize != null) {
            return rooms.size.goe(startSize); // price >= startSize
        }
        if (endSize != null) {
            return rooms.size.loe(endSize); // price <= endSize
        }

        return null;
    }

    private BooleanExpression inEquipment(List<Long> equpmentList) {
        return equpmentList == null || equpmentList.isEmpty()
                ? null
                : rooms.id.in(
                JPAExpressions
                        .select(roomEquipments.rooms.id)
                        .from(roomEquipments)
                        .where(roomEquipments.equipments.id.in(
                                equpmentList
                        ))
        );
    }
}

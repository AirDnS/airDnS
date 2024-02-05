package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.converter.RoomsConverter;
import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.global.address.AddressUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.example.airdns.domain.room.entity.QRooms.rooms;
import static com.example.airdns.domain.roomequipment.entity.QRoomEquipments.roomEquipments;

public class RoomsRepositoryQueryImpl extends QuerydslRepositorySupport implements RoomsRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    //TODO @Value("${}")
    //지도 검색 시 최대 건수 제한, 프런트랑 같이 수정할 것
    private Integer mapSearchLimit = 100;

    public RoomsRepositoryQueryImpl(JPAQueryFactory queryFactory) {
        super(Rooms.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RoomsResponseDto.ReadRoomsListResponseDto> findAllSearchFilter(
            RoomsSearchConditionDto condition) {
        JPAQuery<RoomsResponseDto.ReadRoomsListResponseDto> query = queryFactory.select(
                Projections.fields(RoomsResponseDto.ReadRoomsListResponseDto.class,
                        rooms.id.as("roomsId"),
                        rooms.name,
                        rooms.price,
                        rooms.address,
                        rooms.size,
                        rooms.isClosed,
                        rooms.createdAt,
                        rooms.latitude,
                        rooms.longitude
                        )
            )
            .from(rooms)
            .where(
                    eqName(condition.getKeyword())
                            .or(eqAddress(condition.getKeyword())),
                    betweenPrice(condition.getStartPrice(), condition.getEndPrice()),
                    betweenSize(condition.getStartSize(), condition.getEndSize()),
                    inEquipment(condition.getEqupmentList()),
                    lessThanCursor(condition.getCursor()),
                    nearbyPoiont(
                            condition.getLatitude(),
                            condition.getLongitude(),
                            condition.getSearchDistance()),

                    rooms.isDeleted.isFalse(),
                    rooms.isClosed.isFalse()
            );

        List<RoomsResponseDto.ReadRoomsListResponseDto> roomsResult;
        if (condition.getSearchDistance() == null) {
            roomsResult = query
                    .orderBy(rooms.id.desc())
                    .limit(condition.getPageSize())
                    .fetch();
        } else {
            roomsResult = query
                    .limit(mapSearchLimit)
                    .fetch();
        }

        return roomsResult;
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

    private Page<RoomsResponseDto.ReadRoomsResponseDto> getRoomsResponseDto(
            Pageable pageable, JPQLQuery<Rooms> query) {
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

    private BooleanExpression lessThanCursor(Long cursor) {
        if (cursor != null) {
            return rooms.id.lt(cursor);
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

    private BooleanExpression nearbyPoiont(Double latitude,
                                           Double longitude,
                                           Double searchDistance) {
        if (searchDistance != null && latitude != null && longitude != null ) {
            //TODO 경도에 따른 거리계산이 잘 되고있지 않음 (좌우)
            return rooms.latitude.between(
                    latitude - AddressUtil.calLatDiff(searchDistance),
                    latitude + AddressUtil.calLatDiff(searchDistance)
            ).and(rooms.longitude.between(
                    longitude - AddressUtil.calLongDiff(searchDistance, latitude),
                    longitude + AddressUtil.calLongDiff(searchDistance, latitude)
            ));
        }

        return null;
    }

    @Override
    public List<Long> findRoomIdsByUserId(Long userId){
        return queryFactory.select(rooms.id)
                .from(rooms)
                .where(rooms.users.id.eq(userId))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        queryFactory.delete(rooms)
                .where(rooms.users.id.eq(userId))
                .execute();
    }

    @Override
    public List<Long> findRoomIds(LocalDateTime deleteTime){
        return queryFactory.select(rooms.id)
                .from(rooms)
                .where(rooms.isDeleted.eq(true)
                        .and(rooms.deletedAt.before(deleteTime)))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteRoomInfo(Long roomId){
        queryFactory.delete(rooms)
                .where(rooms.id.eq(roomId))
                .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public Rooms findDeleteRoomInfo(Rooms room){
        return queryFactory.selectFrom(rooms)
                .join(rooms.users)
                .fetchJoin()
                .where(rooms.id.eq(room.getId()))
                .fetchOne();
    }
}

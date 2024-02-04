package com.example.airdns.domain.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.example.airdns.domain.image.entity.QImages.images;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ImagesRepositoryQueryImpl implements ImagesRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, List<String>> findImageUrlGroupByRoomsId(List<Long> roomsIdList) {
        return queryFactory.select(
                        images.imageUrl,
                        images.rooms.id
                )
                .from(images)
                .where(
                        images.rooms.id.in(roomsIdList)
                )
                .transform(groupBy(images.rooms.id).as(list(images.imageUrl)));
    }

}

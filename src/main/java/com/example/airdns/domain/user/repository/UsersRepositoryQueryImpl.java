package com.example.airdns.domain.user.repository;

import com.example.airdns.domain.user.entity.QUsers;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UsersRepositoryQueryImpl implements UsersRepositoryQuery{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findDeletedUserIds(LocalDateTime deleteTime) {
        QUsers qUsers = QUsers.users;
        return jpaQueryFactory.select(qUsers.id)
                .from(qUsers)
                .where(qUsers.isDeleted.eq(true)
                        .and(qUsers.deletedAt.before(deleteTime)))
                .fetch();
    }

    @Override
    public void deleteById(QUsers qUsers, Long userId){
        jpaQueryFactory.delete(qUsers)
                .where(qUsers.id.eq(userId))
                .execute();
    }
}

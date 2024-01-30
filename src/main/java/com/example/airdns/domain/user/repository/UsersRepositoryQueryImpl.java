package com.example.airdns.domain.user.repository;

import com.example.airdns.domain.user.entity.QUsers;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UsersRepositoryQueryImpl implements UsersRepositoryQuery{
    private final JPAQueryFactory jpaQueryFactory;
    QUsers qUsers = QUsers.users;
    @Override
    public List<Long> findUserIds(LocalDateTime deleteTime) {
        return jpaQueryFactory.select(qUsers.id)
                .from(qUsers)
                .where(qUsers.isDeleted.eq(true)
                        .and(qUsers.deletedAt.before(deleteTime)))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteUserInfo(Long userId){
        jpaQueryFactory.delete(qUsers)
                .where(qUsers.id.eq(userId))
                .execute();
    }
}

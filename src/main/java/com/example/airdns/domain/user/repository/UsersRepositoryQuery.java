package com.example.airdns.domain.user.repository;

import com.example.airdns.domain.user.entity.QUsers;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public interface UsersRepositoryQuery {
    List<Long> findDeletedUserIds(LocalDateTime deleteTime);
    void deleteById(QUsers qUsers, Long userId);
}

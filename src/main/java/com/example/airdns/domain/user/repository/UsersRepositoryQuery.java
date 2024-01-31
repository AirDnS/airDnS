package com.example.airdns.domain.user.repository;

import com.example.airdns.domain.user.entity.QUsers;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public interface UsersRepositoryQuery {
    List<Long> findUserIds(LocalDateTime deleteTime);
    void deleteUserInfo(Long userId);
}

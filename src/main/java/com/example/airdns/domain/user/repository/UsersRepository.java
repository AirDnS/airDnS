package com.example.airdns.domain.user.repository;

import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>, UsersRepositoryQuery {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByIdAndIsDeletedFalse(Long userId);

    List<Users> findByIsDeletedTrueAndDeletedAtBefore(LocalDateTime deleteTime);
}

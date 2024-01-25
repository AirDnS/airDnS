package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long>, RoomsRepositoryQuery {
    List<Rooms> findByIsDeletedTrueAndDeletedAtBefore(LocalDateTime deleteTime);
}

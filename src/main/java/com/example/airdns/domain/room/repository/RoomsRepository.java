package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomsRepository extends JpaRepository<Rooms, Long>, RoomsRepositoryQuery {
    Optional<Rooms> findByIdAndIsDeletedFalse(Long roomsId);
}

package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Rooms, Long>, RoomsRepositoryQuery {
}

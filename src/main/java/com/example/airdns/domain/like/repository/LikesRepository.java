package com.example.airdns.domain.like.repository;

import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByRoomsId(Long roomsId);
    boolean existsByRoomsAndUsers(Rooms room, Users user);
}

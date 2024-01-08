package com.example.airdns.domain.like.repository;

import com.example.airdns.domain.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteByRoomsId(Long roomsId);
}

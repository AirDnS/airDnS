package com.example.airdns.domain.review.repository;

import com.example.airdns.domain.review.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    Optional<Reviews> findByRoomsId(Long roomsId);
    List<Reviews> findAllByRoomsId(Long roomsId);

    Optional<Reviews> existsByRoomsId(Long roomsId);

    Optional<Reviews> findByIdAndUsersId(Long usersId, Long reviewId);
}

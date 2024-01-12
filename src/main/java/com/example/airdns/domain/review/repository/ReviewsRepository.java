package com.example.airdns.domain.review.repository;

import com.example.airdns.domain.review.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<Reviews, Long>, QuerydslPredicateExecutor<Reviews> {

    Optional<Reviews> findByRoomsId(Long roomsId);

    Optional<Reviews> existsByRoomsId(Long roomsId);

    Optional<Reviews> findByIdAndUsersId(Long usersId, Long reviewId);
}

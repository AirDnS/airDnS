package com.example.airdns.domain.review.repository;

import com.example.airdns.domain.review.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}

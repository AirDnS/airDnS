package com.example.airdns.domain.review.service;

import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ReviewsService {
    ReviewsResponseDto.GetReviewResponseDto getReview(Long roomId, Long reviewId);
}

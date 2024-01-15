package com.example.airdns.domain.review.service;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewsService {

    ReviewsResponseDto.ReadReviewResponseDto getReview(Long roomId, Long reviewId);

    Page<ReviewsResponseDto.ReadReviewResponseDto> getReviews(Long roomId, Pageable pageable);

    ReviewsResponseDto.CreateReviewResponseDto addReview(
            Long roomsId, Users user,
            ReviewsRequestDto.AddReviewRequestDto requestDto
    );

    ReviewsResponseDto.UpdateReviewResponseDto modifyReview(
            Long roomsId, Long reviewId, Users user,
            ReviewsRequestDto.UpdateReviewRequestDto requestDto
    );

    ReviewsResponseDto.DeleteReviewResponseDto removeReview(
            Long roomsId, Long reviewId, Users user
    );
}

package com.example.airdns.domain.review.service;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewsService {

    List<ReviewsResponseDto.ReadReviewResponseDto> readReviewList(Long roomId);

    ReviewsResponseDto.CreateReviewResponseDto createReview(
            Long roomsId, Users user,
            ReviewsRequestDto.CreateReviewRequestDto requestDto
    );

    ReviewsResponseDto.UpdateReviewResponseDto updateReview(
            Long roomsId, Long reviewId, Users user,
            ReviewsRequestDto.UpdateReviewRequestDto requestDto
    );

    ReviewsResponseDto.DeleteReviewResponseDto deleteReview(
            Long roomsId, Long reviewId, Users user
    );
}

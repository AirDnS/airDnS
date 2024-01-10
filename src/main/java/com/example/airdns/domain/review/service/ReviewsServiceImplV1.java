package com.example.airdns.domain.review.service;

import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.exception.NotFoundReviewsException;
import com.example.airdns.domain.review.exception.ReviewsExceptionCode;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.room.service.RoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewsServiceImplV1 implements ReviewsService{
    private final ReviewsRepository reviewsRepository;
    private final RoomsService roomsService;

    // 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ReviewsResponseDto.GetReviewResponseDto getReview(Long roomsId, Long reviewId){
        roomsService.findRooms(roomsId);

        Reviews reviews = reviewsRepository.findById(reviewId).orElseThrow(
                ()-> new NotFoundReviewsException(ReviewsExceptionCode.NOT_FOUND_REVIEW)
        );

        return ReviewsResponseDto.GetReviewResponseDto.builder()
                .nickName(reviews.getUsers().getNickName())
                .roomName(reviews.getRooms().getName())
                .createdAt(reviews.getCreatedAt())
                .content(reviews.getContent())
                .build();
    }

    // 리스트 조회

    // 리뷰 작성

    // 리뷰 수정

    // 리뷰 삭제
}

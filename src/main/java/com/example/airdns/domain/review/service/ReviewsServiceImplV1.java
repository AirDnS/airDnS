package com.example.airdns.domain.review.service;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.QReviews;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.exception.*;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewsServiceImplV1 implements ReviewsService{
    private final ReviewsRepository reviewsRepository;
    private final RoomsService roomsService;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewsResponseDto.ReadReviewResponseDto> readReviews(Long roomsId) {
        Rooms room = roomsService.findById(roomsId);

        List<Reviews> reviews = room.getReviewsList();

        List<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = new ArrayList<>();

        for (Reviews review : reviews) {
            ReviewsResponseDto.ReadReviewResponseDto responseDto
                    = ReviewsResponseDto.ReadReviewResponseDto.builder()
                    .nickName(review.getUsers().getNickname())
                    .roomName(room.getName())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .build();

            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public ReviewsResponseDto.CreateReviewResponseDto createReview(
            Long roomsId, Users user,
            ReviewsRequestDto.CreateReviewRequestDto requestDto){
        Rooms room = roomsService.findById(roomsId);

        reviewsRepository.existsByRoomsId(roomsId).orElseThrow(
                ()-> new ReviewAlreadyExistsException(ReviewsExceptionCode.ALREADY_EXISTS_REVIEW)
        );

        Reviews review = Reviews.builder()
                .rooms(room)
                .users(user)
                .content(requestDto.getContent())
                .build();

        reviewsRepository.save(review);
        room.addReview(review);

        return ReviewsResponseDto.CreateReviewResponseDto.builder()
                .nickName(review.getUsers().getNickname())
                .roomName(review.getRooms().getName())
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();
    }

    @Override
    @Transactional
    public ReviewsResponseDto.UpdateReviewResponseDto updateReview(
            Long roomsId, Long reviewId, Users user,
            ReviewsRequestDto.UpdateReviewRequestDto requestDto){
        roomsService.findById(roomsId);

        Reviews review = reviewsRepository.findByIdAndUsersId(user.getId(), reviewId).orElseThrow(
                ()-> new NotModifyReviewException(ReviewsExceptionCode.NOT_MODIFY_REVIEW)
        );

        review.update(requestDto);
        reviewsRepository.save(review);

        return ReviewsResponseDto.UpdateReviewResponseDto.builder()
                .nickName(review.getUsers().getNickname())
                .roomName(review.getRooms().getName())
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();
    }

    @Override
    @Transactional
    public ReviewsResponseDto.DeleteReviewResponseDto deleteReview(
            Long roomsId, Long reviewId, Users user){
        roomsService.findById(roomsId);

        Reviews review = reviewsRepository.findByIdAndUsersId(reviewId, user.getId()).orElseThrow(
                ()-> new NotRemoveReviewException(ReviewsExceptionCode.NOT_DELETE_REVIEW)
        );

        reviewsRepository.deleteById(reviewId);

        return ReviewsResponseDto.DeleteReviewResponseDto.builder()
                .nickName(review.getUsers().getNickname())
                .roomName(review.getRooms().getName())
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();
    }
}

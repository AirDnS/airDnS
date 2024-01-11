package com.example.airdns.domain.review.service;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.exception.*;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewsServiceImplV1 implements ReviewsService{
    private final ReviewsRepository reviewsRepository;
    private final RoomsService roomsService;

    // 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ReviewsResponseDto.ReadReviewResponseDto getReview(Long roomsId, Long reivewId){
        roomsService.findRooms(roomsId);

        // 조회를 하는데, 없다고 이게 오류일 필요가 없을 듯?
        Reviews review = reviewsRepository.findByRoomsId(roomsId).orElse(null);

        return ReviewsResponseDto.ReadReviewResponseDto.builder()
                .nickName(review.getUsers().getNickName())
                .roomName(review.getRooms().getName())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .content(review.getContent())
                .build();
    }

    // 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<ReviewsResponseDto.ReadReviewResponseDto> getReviews(Long roomsId){
        Rooms room = roomsService.findRooms(roomsId);

        List<Reviews> reviews = reviewsRepository.findAllByRoomsId(roomsId);

        return reviews.stream()
                .map(review -> ReviewsResponseDto.ReadReviewResponseDto.builder()
                        .roomName(room.getName())
                        .nickName(review.getUsers().getNickName())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .modifiedAt(review.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
    // 리뷰 작성
    @Override
    @Transactional
    public ReviewsResponseDto.CreateReviewResponseDto addReview(
            Long roomsId, UserDetailsImplV1 userDetails,
            ReviewsRequestDto.AddReviewRequestDto requestDto){
        Rooms room = roomsService.findRooms(roomsId);

        reviewsRepository.existsByRoomsId(roomsId).orElseThrow(
                ()-> new ReviewAlreadyExistsException(ReviewsExceptionCode.ALREADY_EXISTS_REVIEW)
        );

        Reviews review = Reviews.builder()
                .rooms(room)
                .users(userDetails.getUser())
                .content(requestDto.getContent())
                .build();

        reviewsRepository.save(review);
        room.addReview(review);

        return ReviewsResponseDto.CreateReviewResponseDto.builder()
                .nickName(review.getUsers().getNickName())
                .roomName(review.getRooms().getName())
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();
    }

    // 리뷰 수정
    @Override
    @Transactional
    public ReviewsResponseDto.UpdateReviewResponseDto modifyReview(
            Long roomsId, Long reviewId, UserDetailsImplV1 userDetails,
            ReviewsRequestDto.UpdateReviewRequestDto requestDto){
        roomsService.findRooms(roomsId);

        Reviews review = reviewsRepository.findByIdAndUsersId(userDetails.getUser().getId(), reviewId).orElseThrow(
                ()-> new NotModifyReviewException(ReviewsExceptionCode.NOT_MODIFY_REVIEW)
        );

        review.modify(requestDto);
        reviewsRepository.save(review);

        return ReviewsResponseDto.UpdateReviewResponseDto.builder()
                .nickName(review.getUsers().getNickName())
                .roomName(review.getRooms().getName())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .content(review.getContent())
                .build();
    }

    // 리뷰 삭제
    @Override
    @Transactional
    public void removeReview(
            Long roomsId, Long reviewId, UserDetailsImplV1 userDetails){
        roomsService.findRooms(roomsId);

        // 해당 로그인한 유저가 작성한 리뷰가 존재하는지?
        reviewsRepository.findByIdAndUsersId(reviewId, userDetails.getUser().getId()).orElseThrow(
                ()-> new NotRemoveReviewException(ReviewsExceptionCode.NOT_DELETE_REVIEW)
        );

        reviewsRepository.deleteById(reviewId);
    }


}

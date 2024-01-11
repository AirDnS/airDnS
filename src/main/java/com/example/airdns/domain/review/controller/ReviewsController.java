package com.example.airdns.domain.review.controller;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.service.ReviewsService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @GetMapping("/{roomsId}/review/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewsResponseDto.ReadReviewResponseDto>> getReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId){
        ReviewsResponseDto.ReadReviewResponseDto responseDto = reviewsService.getReview(roomsId, reviewId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "리뷰 단건 조회 성공", responseDto)
                );
    }

    /*@GetMapping("/{roomsId}/review")
    public ResponseEntity<CommonResponse<List<ReviewsResponseDto.ReadReviewResponseDto>>> getReviews(
            @PathVariable Long roomsId){
        List<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = reviewsService.getReviews(roomsId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 전체 조회 성공", responseDtoList)
                );
    }*/

    // 전체 리뷰 조회를 생성 날짜 내림 차순 정렬로 10개마다 페이징 처리 구현
    @GetMapping("/{roomsId}/review")
    public ResponseEntity<CommonResponse<Page<ReviewsResponseDto.ReadReviewResponseDto>>> getReviews(
            @PathVariable Long roomsId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = reviewsService.getReviews(roomsId, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 전체 조회 성공", responseDtoList)
                );
    }

    @PostMapping("/{roomsId}/review")
    public ResponseEntity<CommonResponse<ReviewsResponseDto.CreateReviewResponseDto>> addReview(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImplV1 userDetails,
            @Valid ReviewsRequestDto.AddReviewRequestDto requestDto){
        ReviewsResponseDto.CreateReviewResponseDto responseDto = reviewsService.addReview(roomsId, userDetails, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED, "룸 리뷰 작성 성공", responseDto)
        );
    }

    @PatchMapping("/{roomsId}/review/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewsResponseDto.UpdateReviewResponseDto>> modifyReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetailsImplV1 userDetails,
            @Valid ReviewsRequestDto.UpdateReviewRequestDto requestDto){
        ReviewsResponseDto.UpdateReviewResponseDto responseDto = reviewsService.modifyReview(roomsId, reviewId, userDetails, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.NO_CONTENT, "룸 리뷰 수정 성공", responseDto)
                );
    }

    @DeleteMapping("/{roomsId}/review/{reviewId}")
    public ResponseEntity<CommonResponse<Void>> removeReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetailsImplV1 userDetails){
        reviewsService.removeReview(roomsId, reviewId, userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new CommonResponse<>(HttpStatus.NO_CONTENT, "룸 리뷰 삭제 성공", null)
                );
    }
}

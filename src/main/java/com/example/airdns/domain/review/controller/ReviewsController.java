package com.example.airdns.domain.review.controller;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.service.ReviewsService;
import com.example.airdns.global.common.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @GetMapping("/{roomsId}/review/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewsResponseDto.GetReviewResponseDto>> getReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId){
        ReviewsResponseDto.GetReviewResponseDto responseDto = reviewsService.getReview(roomsId, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK, "리뷰 단건 조회 성공", responseDto));
    }

    @GetMapping("/{roomsId}/review")
    public ResponseEntity<CommonResponse<ReviewsRequestDto>> getReviews(){

        return null;
    }

    @PostMapping("/{roomsId}/review")
    public ResponseEntity<CommonResponse<ReviewsRequestDto>> addReview(){

        return null;
    }

    @PatchMapping("/{roomsId}/review/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewsRequestDto>> modifyReview(){

        return null;
    }

    @DeleteMapping("/{roomsId}/review/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewsRequestDto>> deleteReview(){

        return null;
    }
}

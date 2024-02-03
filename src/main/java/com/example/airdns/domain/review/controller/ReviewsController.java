package com.example.airdns.domain.review.controller;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.service.ReviewsService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "review", description = "Review API")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @GetMapping("/{roomsId}/review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 리뷰 전체 조회 성공"),
    })
    public ResponseEntity<CommonResponse<List<ReviewsResponseDto.ReadReviewResponseDto>>> readReviewList(
            @PathVariable Long roomsId){
        List<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = reviewsService.readReviewList(roomsId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 전체 조회 성공", responseDtoList)
                );
    }

    @PostMapping("/{roomsId}/review")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "룸 리뷰 작성 성공"),
            @ApiResponse(responseCode = "400", description = "해당 방에 대한 리뷰가 이미 존재합니다."),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.CreateReviewResponseDto>> createReview(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewsRequestDto.CreateReviewRequestDto requestDto){
        ReviewsResponseDto.CreateReviewResponseDto responseDto = reviewsService.createReview(roomsId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED, "룸 리뷰 작성 성공", responseDto)
        );
    }

    @PatchMapping("/{roomsId}/review/{reviewId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 리뷰 수정 성공"),
            @ApiResponse(responseCode = "403", description = "해당 방에 대한 리뷰를 수정할 권한이 없습니다."),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.UpdateReviewResponseDto>> updateReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewsRequestDto.UpdateReviewRequestDto requestDto){
        ReviewsResponseDto.UpdateReviewResponseDto responseDto = reviewsService.updateReview(roomsId, reviewId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 수정 성공", responseDto)
                );
    }

    @DeleteMapping("/{roomsId}/review/{reviewId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 리뷰 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "해당 방에 대한 리뷰를 삭제할 권한이 없습니다."),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.DeleteReviewResponseDto>> deleteReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReviewsResponseDto.DeleteReviewResponseDto responseDto = reviewsService.deleteReview(roomsId, reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 삭제 성공", responseDto)
                );
    }
}

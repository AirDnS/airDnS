package com.example.airdns.domain.review.controller;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.service.ReviewsService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @GetMapping("/{roomsId}/review/{reviewId}")
    @Operation(summary = "Create Review", description = "해당 방에 리뷰를 적는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 단건 조회 성공"),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.ReadReviewResponseDto>> getReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId){
        ReviewsResponseDto.ReadReviewResponseDto responseDto = reviewsService.getReview(roomsId, reviewId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "리뷰 단건 조회 성공", responseDto)
                );
    }

    // 전체 리뷰 조회를 생성 날짜 내림 차순 정렬로 10개마다 페이징 처리 구현
    @GetMapping("/{roomsId}/review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 리뷰 전체 조회 성공"),
    })
    public ResponseEntity<CommonResponse<Page<ReviewsResponseDto.ReadReviewResponseDto>>> getReviews(
            @PathVariable Long roomsId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = reviewsService.getReviews(roomsId, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 전체 조회 성공", responseDtoList)
                );
    }

    @PostMapping("/{roomsId}/review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "룸 리뷰 작성 성공"),
            @ApiResponse(responseCode = "400", description = "해당 방에 대한 리뷰가 이미 존재합니다."),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.CreateReviewResponseDto>> addReview(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid ReviewsRequestDto.AddReviewRequestDto requestDto){
        ReviewsResponseDto.CreateReviewResponseDto responseDto = reviewsService.addReview(roomsId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(HttpStatus.CREATED, "룸 리뷰 작성 성공", responseDto)
        );
    }

    @PatchMapping("/{roomsId}/review/{reviewId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 리뷰 수정 성공"),
            @ApiResponse(responseCode = "403", description = "해당 방에 대한 리뷰를 수정할 권한이 없습니다."),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.UpdateReviewResponseDto>> modifyReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid ReviewsRequestDto.UpdateReviewRequestDto requestDto){
        ReviewsResponseDto.UpdateReviewResponseDto responseDto = reviewsService.modifyReview(roomsId, reviewId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 수정 성공", responseDto)
                );
    }

    @DeleteMapping("/{roomsId}/review/{reviewId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 리뷰 수정 성공"),
            @ApiResponse(responseCode = "403", description = "해당 방에 대한 리뷰를 삭제할 권한이 없습니다."),
    })
    public ResponseEntity<CommonResponse<ReviewsResponseDto.DeleteReviewResponseDto>> removeReview(
            @PathVariable Long roomsId,
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReviewsResponseDto.DeleteReviewResponseDto responseDto = reviewsService.removeReview(roomsId, reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(HttpStatus.OK, "룸 리뷰 삭제 성공", responseDto)
                );
    }
}

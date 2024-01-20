package com.example.airdns.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReviewsResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "룸의 리뷰 조회 응답 dto")
    public static class ReadReviewResponseDto {
        private Long reviewsId;
        private Long usersId;
        @Schema(description = "추가할 이름", defaultValue = "read Review")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "리뷰 단건 추가 응답 dto")
    public static class CreateReviewResponseDto {
        @Schema(description = "리뷰 추가 내용", defaultValue = "create Review")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "리뷰 단건 수정 응답 dto")
    public static class UpdateReviewResponseDto {
        private Long reviewsId;
        private Long usersId;
        @Schema(description = "리뷰 수정 내용", defaultValue = "update Review")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "리뷰 단건 삭제 응답 dto")
    public static class DeleteReviewResponseDto {
        @Schema(description = "리뷰 삭제 내용", defaultValue = "delete Review")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private String content;
    }
}

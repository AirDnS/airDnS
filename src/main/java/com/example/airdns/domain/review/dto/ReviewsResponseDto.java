package com.example.airdns.domain.review.dto;

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
    // @Schema(description = 리뷰 조회 응답 dto")
    public static class ReadReviewResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 추가 응답 dto")
    public static class CreateReviewResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 추가 요청 dto")
    public static class UpdateReviewResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private String content;
    }

}

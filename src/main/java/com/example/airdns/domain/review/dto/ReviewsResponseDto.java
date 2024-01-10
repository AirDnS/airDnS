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
    // @Schema(description = 리뷰 단건 조회 응답 dto")
    public static class GetReviewResponseDto {
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
    // @Schema(description = 리뷰 리스트 조회 응답 dto")
    public static class GetReviewListResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 추가 응답 dto")
    public static class AddReviewResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 추가 요청 dto")
    public static class ModifyReviewResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 삭제 응답 dto")
    public static class DeleteReviewResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String content;
    }
}

package com.example.airdns.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewsRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 추가 요청 dto")
    public static class AddReviewRequestDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = 리뷰 단건 추가 요청 dto")
    public static class UpdateReviewRequestDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String content;
    }
}

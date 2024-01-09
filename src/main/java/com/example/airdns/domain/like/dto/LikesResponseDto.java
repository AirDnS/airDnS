package com.example.airdns.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LikesResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = "좋아요 요청 dto")
    public static class GetLikeListResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String nickName;
        private String roomName;
        private String roomAddress;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = "좋아요 요청 dto")
    public static class AddLikeResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // @Schema(description = "좋아요 요청 dto")
    public static class DeleteLikeResponseDto {
        // @Schema(description = "추가할 이름", defaultValue = "test")
        private String nickName;
        private String roomName;
    }

}

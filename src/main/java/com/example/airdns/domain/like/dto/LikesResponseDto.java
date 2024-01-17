package com.example.airdns.domain.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "좋아요 조회 응답 dto")
    public static class ReadLikeResponseDto {
        @Schema(description = "좋아요 조회 응답 내용", defaultValue = "get like response")
        private String nickName;
        private String roomName;
        private String roomAddress;
        private LocalDateTime createdAt;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "좋아요 추가 응답 dto")
    public static class CreateLikeResponseDto {
        @Schema(description = "좋아요 추가 응답 내용", defaultValue = "add like response")
        private String nickName;
        private String roomName;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "좋아요 삭제 응답 dto")
    public static class DeleteLikeResponseDto {
        @Schema(description = "좋아요 삭제 응답 내용", defaultValue = "delete like response")
        private String nickName;
        private String roomName;
    }

}

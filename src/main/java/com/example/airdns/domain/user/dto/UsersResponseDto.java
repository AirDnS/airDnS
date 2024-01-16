package com.example.airdns.domain.user.dto;

import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UsersResponseDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "유저 정보 수정 응당 dto")
    public static class UpdateUsersResponseDto {

        @Schema(description = "닉네임", example = "양배추123", defaultValue = "양배추123")
        private String nickname;

        @Schema(description = "주소", example = "서울특별시 서초구 서초대로77길 54", defaultValue = "서울특별시 서초구 서초대로77길 54")
        private String address;

        @Schema(description = "전화번호", example = "010-1234-1234", defaultValue = "010-1234-1234")
        private String contact;

        @Schema(description = "역할", example = "ROLE_USER", defaultValue = "ROLE_USER")
        private UserRole role;

        public static UpdateUsersResponseDto of(Users user) {
            return UpdateUsersResponseDto.builder()
                    .nickname(user.getNickname())
                    .address(user.getAddress())
                    .contact(user.getContact())
                    .role(user.getRole())
                    .build();
        }
    }
}

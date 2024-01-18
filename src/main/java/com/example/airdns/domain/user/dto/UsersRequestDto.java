package com.example.airdns.domain.user.dto;

import com.example.airdns.domain.user.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UsersRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "유저 정보 수정 dto")
    public static class UpdateUserInfoRequestDto {

        @Schema(description = "닉네임", example = "양배추123", defaultValue = "양배추123")
        @Size(min= 2, max=10)
        private String nickname;

        @Schema(description = "전화번호", example = "010-1234-1234", defaultValue = "010-1234-1234")
        @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 수인 숫자만 입력 가능합니다.")
        private String contact;

        @Schema(description = "주소", example = "서울특별시 서초구 서초대로77길 54", defaultValue = "서울특별시 서초구 서초대로77길 54")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s-]+$")
        private String address;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "유저 권한 수정 dto")
    public static class UpdateUserRoleRequestDto {

        @Schema(description = "권한", example = "HOST", defaultValue = "HOST")
        private UserRole role;
    }
}

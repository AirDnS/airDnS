package com.example.airdns.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;

public class UserDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponseDto {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoUserInfoDto {
        private Long id;
        private String nickname;
        private String email;
    }

}

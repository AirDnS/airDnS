package com.example.airdns.domain.user.controller;

import com.example.airdns.domain.oauth2.common.OAuth2UserPrincipal;
import com.example.airdns.domain.user.dto.UserDto;
import com.example.airdns.domain.user.service.UsersService;
import com.example.airdns.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UsersService usersService;


    @Operation(summary = "유저 프로필 수정")
    @ResponseBody
    @PatchMapping("/users/{usersId}/profile")
    public ResponseEntity<CommonResponse<UserDto.UserUpdateResponseDto>> updateProfile(
            @AuthenticationPrincipal OAuth2UserPrincipal oAuth2UserPrincipal,
            @RequestBody UserDto.UserRequestDto userRequestDto,
            @PathVariable Long usersId) {
        System.out.println("update");
        UserDto.UserUpdateResponseDto responseDto = usersService.updateUser(usersId, oAuth2UserPrincipal.getUsers(), userRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "프로필 수정 성공", responseDto)
        );
    }

}
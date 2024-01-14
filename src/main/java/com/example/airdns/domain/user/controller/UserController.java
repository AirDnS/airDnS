package com.example.airdns.domain.user.controller;

import com.example.airdns.domain.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UsersService usersService;

//
//    @Operation(summary = "유저 프로필 수정")
//    @ResponseBody
//    @PatchMapping("/users/{usersId}/profile")
//    public ResponseEntity<CommonResponse<UserDto.UserUpdateResponseDto>> updateProfile(
//            @AuthenticationPrincipal Oauth2UserService oauth2UserService,
//            @RequestBody UserDto.UserRequestDto userRequestDto,
//            @PathVariable Long usersId) {
//        System.out.println("update");
//        UserDto.UserUpdateResponseDto responseDto = usersService.updateUser(usersId, oauth2UserService.getUser(), userRequestDto);
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new CommonResponse<>(HttpStatus.OK, "프로필 수정 성공", responseDto)
//        );
//    }

}
package com.example.airdns.domain.user.controller;

import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.service.UsersService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 API")
@RequestMapping("/api/v1")
public class UsersController {

    private final UsersService usersService;


    @PatchMapping("/users/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "유저 정보 수정", description = "사용중 유저의 닉네임과 주소 핸드폰 번호 변경이 가능하다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 변경 성공",
                    content = {@Content(schema = @Schema(implementation = UsersResponseDto.UpdateUsersResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "입력한 정보가 유효하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "해당 유저는 존재하지 않습니다")
    })
    public ResponseEntity<CommonResponse<UsersResponseDto.UpdateUsersResponseDto>> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl UserDetailsImpl,
            @Valid @RequestBody UsersRequestDto.UpdateUserInfoRequestDto userRequestDto) {

        UsersResponseDto.UpdateUsersResponseDto responseDto = usersService.updateUser(UserDetailsImpl.getUser().getId()
                , userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "프로필 수정 성공", responseDto)
        );
    }

    @PatchMapping("/users/role")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "유저 권한 수정", description = "사용중 유저의 권한을 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 권한 변경 성공",
                    content = {@Content(schema = @Schema(implementation = UsersResponseDto.UpdateUsersResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "입력한 정보가 유효하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "해당 유저는 존재하지 않습니다")
    })
    public ResponseEntity<CommonResponse<UsersResponseDto.UpdateUsersResponseDto>> updateRole(
            @AuthenticationPrincipal UserDetailsImpl UserDetailsImpl) {

        UsersResponseDto.UpdateUsersResponseDto responseDto = usersService.updateUserRole(UserDetailsImpl.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "권한 변경 성공", responseDto)
        );
    }

    @GetMapping("/users")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "유저 조회", description = "유저 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공",
                    content = {@Content(schema = @Schema(implementation = UsersResponseDto.ReadUserResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "입력한 정보가 유효하지 않습니다"),
            @ApiResponse(responseCode = "404", description = "해당 유저는 존재하지 않습니다")
    })
    public ResponseEntity readUserInfo(
            @AuthenticationPrincipal UserDetailsImpl UserDetailsImpl) {

        UsersResponseDto.ReadUserResponseDto responseDto = usersService.readUserInfo(UserDetailsImpl.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "유저 정보 조회 성공", responseDto)
        );
    }

    @GetMapping("/users/silence")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "유저 조회", description = "유저 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "의미없는 요청 성공",
                    content = {@Content(schema = @Schema(implementation = UsersResponseDto.ReadUserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "해당 유저는 존재하지 않습니다")
    })
    public ResponseEntity readUserSilence(
            @AuthenticationPrincipal UserDetailsImpl UserDetailsImpl) {
        usersService.readUserSilence(UserDetailsImpl.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "Silence Login")
        );
    }
}
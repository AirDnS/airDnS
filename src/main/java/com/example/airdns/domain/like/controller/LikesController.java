package com.example.airdns.domain.like.controller;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.service.LikesService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class LikesController {

    private final LikesService likesService;

    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "룸에 대한 좋아요 갯수 조회"),
    })
    @GetMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<LikesResponseDto.ReadLikeResponseDto>> getRoomLike(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikesResponseDto.ReadLikeResponseDto responseDto = likesService.getRoomLike(roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "룸 좋아요 조회 성공", responseDto)
        );
    }

    @PostMapping("/{roomsId}/likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 좋아요 성공"),
    })
    public ResponseEntity<CommonResponse<LikesResponseDto.CreateLikeResponseDto>> addLike(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikesResponseDto.CreateLikeResponseDto responseDto = likesService.addLike(roomsId, userDetails.getUser());
        // ok
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(
                        HttpStatus.OK, "룸 좋아요 성공", responseDto
                )
        );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "룸 좋아요 취소 성공"),
            @ApiResponse(responseCode = "400", description = "해당 사용자가 좋아요를 누르지 않았습니다."),
    })
    @DeleteMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<LikesResponseDto.DeleteLikeResponseDto>> cancelLike(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        LikesResponseDto.DeleteLikeResponseDto responseDto = likesService.cancelLike(roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "룸 좋아요 취소 성공", responseDto)
        );
    }
}

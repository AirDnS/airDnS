package com.example.airdns.domain.like.controller;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.service.LikesService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/rooms")
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<List<LikesResponseDto.GetLikeResponseDto>>> getLikeList(
            @PathVariable Long roomsId,
            Users user){
        List<LikesResponseDto.GetLikeResponseDto> responseDto = likesService.getLikeList(roomsId, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse<>(HttpStatus.CREATED, "룸 좋아요 목록 조회 성공", responseDto)
        );
    }

    @PostMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<LikesResponseDto.AddLikeResponseDto>> addLike(
            @PathVariable Long roomsId,
            Users users){
        LikesResponseDto.AddLikeResponseDto responseDto = likesService.addLike(roomsId, users);
        // ok
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(
                        HttpStatus.OK, "룸 좋아요 성공", responseDto
                )
        );
    }

    @DeleteMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<LikesResponseDto.DeleteLikeResponseDto>> cancelLike(
            @PathVariable Long roomsId,
            Users user){
        LikesResponseDto.DeleteLikeResponseDto responseDto = likesService.cancelLike(roomsId, user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(HttpStatus.OK, "룸 좋아요 취소 성공", responseDto)
        );
    }
}

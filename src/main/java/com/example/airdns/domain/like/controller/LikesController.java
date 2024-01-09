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
    public ResponseEntity<CommonResponse<List<LikesResponseDto.GetLikeListResponseDto>>> getLikeList(
            @PathVariable Long roomsId,
            Users user){
        List<LikesResponseDto.GetLikeListResponseDto> responseDto = likesService.getLikeList(roomsId, user);
        return new ResponseEntity<>(
                new CommonResponse<>(HttpStatus.CREATED,"룸 좋아요 조회 성공",responseDto),
                HttpStatus.CREATED);
    }

    @PostMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<LikesResponseDto.AddLikeResponseDto>> addLike(
            @PathVariable Long roomsId,
            Users users){
        LikesResponseDto.AddLikeResponseDto responseDto = likesService.postLike(roomsId, users);
        return new ResponseEntity<>(
                new CommonResponse<>(HttpStatus.OK,"msg",responseDto),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{roomsId}/likes")
    public ResponseEntity<CommonResponse<LikesResponseDto.DeleteLikeResponseDto>> cancelLike(
            @PathVariable Long roomsId,
            Users user){
        LikesResponseDto.DeleteLikeResponseDto responseDto = likesService.deleteLike(roomsId, user);
        return new ResponseEntity<>(new CommonResponse<>(
                HttpStatus.OK,"msg",responseDto),
                HttpStatus.OK);
    }
}

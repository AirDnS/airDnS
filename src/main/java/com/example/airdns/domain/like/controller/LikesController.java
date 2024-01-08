package com.example.airdns.domain.like.controller;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.service.LikesService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/rooms")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/{roomsId}/like")
    public ResponseEntity<CommonResponse<LikesResponseDto>> addLike(
            @PathVariable Long roomsId,
            Users users){
        LikesResponseDto responseDto = likesService.postLike(roomsId, users);
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,"msg",responseDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{roomsId}/like")
    public ResponseEntity<CommonResponse<LikesResponseDto>> cancelLike(
            @PathVariable Long roomsId,
            Users user
            // @AuthenitcationPrincipal UserDetails userDetails
            ){
        LikesResponseDto responseDto = likesService.deleteLike(roomsId, user);
        return new ResponseEntity<>(new CommonResponse<>(HttpStatus.OK,"msg",responseDto), HttpStatus.NO_CONTENT);
    }
}

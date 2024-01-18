package com.example.airdns.domain.like.service;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.user.entity.Users;

import java.util.List;

public interface LikesService {

    LikesResponseDto.CreateLikeResponseDto createLike(Long roomsId, Users user);
    LikesResponseDto.DeleteLikeResponseDto deleteLike(Long roomsId, Long likeId, Users user);
    LikesResponseDto.ReadLikeResponseDto readRoomLike(Long roomsId, Long likeId);
}

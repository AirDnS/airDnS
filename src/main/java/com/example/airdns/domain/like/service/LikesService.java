package com.example.airdns.domain.like.service;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.user.entity.Users;

import java.util.List;

public interface LikesService {

    List<LikesResponseDto.GetLikeResponseDto> getLikeList(Long roomsId, Users user);
    LikesResponseDto.AddLikeResponseDto addLike(Long roomsId, Users user);
    LikesResponseDto.DeleteLikeResponseDto cancelLike(Long roomsId, Users user);
}

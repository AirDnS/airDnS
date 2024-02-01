package com.example.airdns.domain.like.service;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.user.entity.Users;

import java.util.List;

public interface LikesService {

    /**
     * 룸에 대한 좋아요
     * @param roomsId 룸 아이디
     * @param user 로그인 유저
     * @return 생성된 룸에 대한 좋아요 유저 이름과 룸 이름
     */
    LikesResponseDto.CreateLikeResponseDto createLike(Long roomsId, Users user);

    /**
     * 룸에 대한 좋아요 취소
     * @param roomsId 방 아이디
     * @param likeId 좋아요 아이디
     * @param user 로그인 유저
     * @return 좋아요 취소한 룸에 대한 유저 이름, 방 이름
     */
    LikesResponseDto.DeleteLikeResponseDto deleteLike(Long roomsId, Long likeId, Users user);

    /**
     * 룸에 대한 좋아요 리스트 조회
     * @param roomsId 방 아이디
     * @return 방에 대한 좋아요 숫자
     */
    LikesResponseDto.ReadLikeResponseDto readRoomLike(Long roomsId);
}

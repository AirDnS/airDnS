package com.example.airdns.domain.like.service;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.exception.LikesExceptionCode;
import com.example.airdns.domain.like.exception.UserNotLikedException;
import com.example.airdns.domain.like.repository.LikesRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesServiceImplV1 implements LikesService {

    private final LikesRepository likesRepository;
    private final RoomsService roomsService;

    // 해당 룸에 대한 모든 좋아요 조회
    @Override
    @Transactional(readOnly = true)
    public LikesResponseDto.ReadLikeResponseDto getRoomLike(Long roomsId, Users user){
        Rooms room = roomsService.findById(roomsId);

        Integer roomLikeCount = room.getLikesList().size();

        return LikesResponseDto.ReadLikeResponseDto.builder()
                .likeCount(roomLikeCount)
                .build();
    }

    @Override
    @Transactional
    public LikesResponseDto.CreateLikeResponseDto addLike(Long roomsId, Users user){
        Rooms room = roomsService.findById(roomsId);

        Likes likes = Likes.builder()
                .rooms(room)
                .users(user)
                .build();

        // room.addLike(likes);
        likesRepository.save(likes);

        return LikesResponseDto.CreateLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickname())
                .build();
    }

    @Override
    @Transactional
    public LikesResponseDto.DeleteLikeResponseDto cancelLike(Long roomsId, Users user) {
        Rooms room = roomsService.findById(roomsId);

        likesRepository.findByRoomsId(roomsId).orElseThrow(
                ()-> new UserNotLikedException(LikesExceptionCode.USER_NOT_LIKED)
        );

        likesRepository.deleteByRoomsId(roomsId);

        return LikesResponseDto.DeleteLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickname())
                .build();
    }

}

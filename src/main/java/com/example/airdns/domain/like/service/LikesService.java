package com.example.airdns.domain.like.service;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.repository.LikesRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final RoomsService roomsService;
    @Transactional
    public LikesResponseDto.AddLikeResponseDto postLike(Long roomsId, Users user){
        // #9 : 해당 부분은 테스트 용으로 사용, Rooms과 협의 후 설정
        Rooms room = roomsService.findRooms(roomsId);

        Likes likes = Likes.builder()
                .rooms(room)
                .users(user)
                .build();
        likesRepository.save(likes);

        LikesResponseDto.AddLikeResponseDto responseDto = LikesResponseDto.AddLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickName())
                .build();

        return responseDto;
    }

    @Transactional
    public LikesResponseDto.DeleteLikeResponseDto deleteLike(Long roomsId, Users user) {
        Rooms room = roomsService.findRooms(roomsId);

        likesRepository.deleteByRoomsId(roomsId);

        LikesResponseDto.DeleteLikeResponseDto responseDto = LikesResponseDto.DeleteLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickName())
                .build();

        return responseDto;
    }
}

package com.example.airdns.domain.like.service;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.exception.LikesCustomException;
import com.example.airdns.domain.like.exception.LikesExceptionCode;
import com.example.airdns.domain.like.repository.LikesRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesServiceImplV1 implements LikesService {

    private final LikesRepository likesRepository;
    private final RoomsService roomsService;
    private final UsersService usersService;

    @Override
    @Transactional(readOnly = true)
    public LikesResponseDto.ReadLikeResponseDto readRoomLike(Long roomsId){

        Rooms room = roomsService.findById(roomsId);
        Users user = usersService.findById(room.getUsers().getId());
        Boolean userLiked = likesRepository.existsByRoomsAndUsers(room, user);

        Integer roomLikeCount = room.getLikesList().size();
        room.addLikeCount(roomLikeCount);

        return LikesResponseDto.ReadLikeResponseDto.builder()
                .roomName(room.getName())
                .ownerName(room.getUsers().getNickname())
                .likeCount(roomLikeCount)
                .userLiked(userLiked)
                .build();
    }

    @Override
    @Transactional
    public LikesResponseDto.CreateLikeResponseDto createLike(Long roomsId, Users user){
        Rooms room = roomsService.findById(roomsId);

        if (likesRepository.existsByRoomsAndUsers(room, user)) {
            throw new LikesCustomException(LikesExceptionCode.ALREDAY_EXIST_LIKES);
        }

        Likes likes = Likes.builder()
                .rooms(room)
                .users(user)
                .build();

        // room.addLike(likes);
        likesRepository.save(likes);

        return LikesResponseDto.CreateLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickname())
                .userLiked(true)
                .build();
    }

    @Override
    @Transactional
    public LikesResponseDto.DeleteLikeResponseDto deleteLike(Long roomsId, Long likeId, Users user) {
        Rooms room = roomsService.findById(roomsId);

        Likes like = likesRepository.findByRoomsId(roomsId).orElseThrow(
                ()-> new LikesCustomException(LikesExceptionCode.USER_NOT_LIKED)
        );

        likesRepository.delete(like);

        return LikesResponseDto.DeleteLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickname())
                .build();
    }
}

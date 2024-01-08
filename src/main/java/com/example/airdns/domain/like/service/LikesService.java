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

    // 0. LikeRequestDto를 설정할지? 만약에 하게 된다면 어떤식으로 접근하는게 좋을지?
    // 1. findRoom() 메서드를 사용해서 roomsService 접근에 대해 어떻게 생각하는지?
    // 2. LikesServiceTest에서 해당 Test에 findRooms에 대한 테스트 코드 만들 것인지?
    // 3. LikesResponseDto.AddLikeResponseDto 이런식의 사용 형태가 맞는지?
    // 아니면 더 좋은 방법이 있을지?
    // 4. NO_CONTENT에 대해서 responseDto를 반환 할지 말지

    private final LikesRepository likesRepository;
    private final RoomsService roomsService;
    @Transactional
    public LikesResponseDto.AddLikeResponseDto postLike(Long roomsId, Users user){
        Rooms room = roomsService.findRooms(roomsId);

        Likes likes = Likes.builder()
                .rooms(room)
                .users(user)
                .build();
        likesRepository.save(likes);

        return LikesResponseDto.AddLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickName())
                .build();
    }

    @Transactional
    public LikesResponseDto.DeleteLikeResponseDto deleteLike(Long roomsId, Users user) {
        Rooms room = roomsService.findRooms(roomsId);

        likesRepository.deleteByRoomsId(roomsId);

        return LikesResponseDto.DeleteLikeResponseDto.builder()
                .roomName(room.getName())
                .nickName(user.getNickName())
                .build();
    }
}

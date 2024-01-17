package com.example.airdns.like;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.exception.UserNotLikedException;
import com.example.airdns.domain.like.repository.LikesRepository;
import com.example.airdns.domain.like.service.LikesServiceImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikesServiceTest {

    @InjectMocks
    private LikesServiceImplV1 likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private RoomsService roomsService;

    @Test
    @DisplayName("LikeService addLike Success")
    void addLikeSuccess() {
        // given
        Long roomId = 1L;
        Users user = Users.builder().nickname("User1").build();
        Rooms room = Rooms.builder().id(roomId).name("Room Number1").address("Room1 Address").build();
        Likes savedLike = Likes.builder()
                .rooms(room)
                .users(user)
                .build();

        when(roomsService.findById(roomId)).thenReturn(room);
        when(likesRepository.save(org.mockito.ArgumentMatchers.any(Likes.class))).thenReturn(savedLike);

        // when
        LikesResponseDto.CreateLikeResponseDto result = likesService.addLike(roomId, user);

        // then
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(user.getNickname(), result.getNickName());
        assertEquals(savedLike.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    @DisplayName("LikeService cancelLike Success")
    void cancelLikeSuccess() {
        // given
        Long roomId = 1L;
        Users user = Users.builder().nickname("User1").build();
        Rooms room = Rooms.builder().id(roomId).name("Room Number1").address("Room1 Address").build();
        Likes existingLike = Likes.builder().rooms(room).users(user).build();

        when(roomsService.findById(roomId)).thenReturn(room);
        when(likesRepository.findByRoomsId(roomId)).thenReturn(Optional.of(existingLike));

        // when
        LikesResponseDto.DeleteLikeResponseDto result = likesService.cancelLike(roomId, user);

        // then
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(user.getNickname(), result.getNickName());
    }

    @Test
    @DisplayName("LikesService cancelLike UserNotLikedException")
    void cancelLikeUserNotLikedException() {
        // given
        Long roomId = 1L;
        Users user = Users.builder().nickname("User1").build();

        when(likesRepository.findByRoomsId(roomId)).thenReturn(Optional.empty());

        // when & then
        UserNotLikedException exception = assertThrows(UserNotLikedException.class, () -> {
            likesService.cancelLike(roomId, user);
        });

        assertEquals("해당 사용자가 좋아요를 누르지 않았습니다.", exception.getMessage());
    }
}
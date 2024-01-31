package com.example.airdns.like;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.exception.LikesCustomException;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikesServiceTest {

    @InjectMocks
    private LikesServiceImplV1 likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private RoomsService roomsService;

    @Test
    @DisplayName("LikesService readRoomLike Success")
    void readRoomLikeSuccess(){
        // given
        Long roomId = 1L;
        Rooms room = mock(Rooms.class);
        when(roomsService.findById(roomId)).thenReturn(room);

        List<Likes> likesList = IntStream.range(0, 5)
                .mapToObj(i -> Likes.builder().build())
                .collect(Collectors.toList());

        when(room.getLikesList()).thenReturn(likesList);

        // when
        LikesResponseDto.ReadLikeResponseDto response = likesService.readRoomLike(roomId);

        // then
        assertEquals(5, response.getLikeCount());
    }

    @Test
    @DisplayName("LikeService createLike Success")
    void createLikeSuccess() {
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
        LikesResponseDto.CreateLikeResponseDto result = likesService.createLike(roomId, user);

        // then
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(user.getNickname(), result.getNickName());
    }

    @Test
    @DisplayName("LikesService createLike AlreadyExistLikesException")
    void createLikeAlreadyExistLikes() {
        // given
        Long roomId = 1L;
        Users user = Users.builder().nickname("User Nickname").build();
        Rooms room = Rooms.builder().name("Room Name").build();

        when(roomsService.findById(roomId)).thenReturn(room);
        when(likesRepository.existsByRoomsAndUsers(room, user)).thenReturn(true);

        // when, then
        LikesCustomException exception = assertThrows(LikesCustomException.class, () -> {
            likesService.createLike(roomId, user);
        });

        assertEquals("LIKES-002", exception.getErrorCode());
        assertEquals("해당 사용자는 좋아요를 이미 눌렀습니다.", exception.getMessage());
        assertEquals("400 BAD_REQUEST", exception.getHttpStatus().toString());
    }

    @Test
    @DisplayName("LikeService cancelLike Success")
    void deleteLikeSuccess() {
        // given
        Long roomId = 1L;
        Users user = Users.builder().nickname("User1").build();
        Rooms room = Rooms.builder().id(roomId).name("Room Number1").address("Room1 Address").build();
        Likes existingLike = Likes.builder().rooms(room).users(user).build();

        when(roomsService.findById(roomId)).thenReturn(room);
        when(likesRepository.findByRoomsId(roomId)).thenReturn(Optional.of(existingLike));

        // when
        LikesResponseDto.DeleteLikeResponseDto result = likesService.deleteLike(roomId, existingLike.getId(), user);

        // then
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(user.getNickname(), result.getNickName());
    }

    @Test
    @DisplayName("LikesService deleteLike UserNotLikedException")
    void deleteLikeUserNotLikedException() {
        // given
        Long roomId = 1L;
        Users user1 = Users.builder().nickname("User1").build();
        Users user2 = Users.builder().nickname("User2").build();
        Rooms room = Rooms.builder().id(roomId).name("Room Number1").address("Room1 Address").build();
        Likes like = Likes.builder().rooms(room).users(user1).build();

        when(likesRepository.findByRoomsId(roomId)).thenReturn(Optional.empty());

        // when & then
        LikesCustomException exception = assertThrows(LikesCustomException.class, () -> {
            likesService.deleteLike(roomId, like.getId() ,user2);
        });

        assertEquals("LIKES-001", exception.getErrorCode());
        assertEquals("해당 사용자가 좋아요를 누르지 않았습니다.", exception.getMessage());
        assertEquals("400 BAD_REQUEST", exception.getHttpStatus().toString());
    }
}
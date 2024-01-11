package com.example.airdns.like;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.exception.LikesExceptionCode;
import com.example.airdns.domain.like.exception.UserNotLikedException;
import com.example.airdns.domain.like.service.LikesServiceImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.repository.LikesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikesServiceTest {

    @InjectMocks
    private LikesServiceImplV1 likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private RoomsService roomsService;

    @Test
    @DisplayName("LikesService getRoomsLikeList Success")
    void getRoomLikeListSuccess() {
        // given
        Long roomsId = 1L;
        Users user = createUsers();
        Rooms room = createRooms();

        when(roomsService.findById(anyLong())).thenReturn(room);
        when(likesRepository.findAllByRoomsId(roomsId)).thenReturn(Arrays.asList(
                Likes.builder().build(),
                Likes.builder().build()
        ));

        // when
        List<LikesResponseDto.GetLikeResponseDto> result = likesService.getLikeList(roomsId, user);

        // then
        assertEquals(2, result.size());
        assertEquals("Test Room", result.get(0).getRoomName());
        assertEquals("TestAddress", result.get(0).getRoomAddress());
        assertEquals("TestUser", result.get(0).getNickName());
        // 여러 다른 assertEquals 작업을 추가하면 됩니다.
    }

    @Test
    @DisplayName("LikesService addLike Success")
    void roomsLikeSuccess() {
        // given
        Long roomsId = 2L;
        Users user = Users.builder().id(1L).nickName("TestUser").build();
        Rooms room = Rooms.builder().id(roomsId).name("TestRoom").build();

        when(roomsService.findById(anyLong())).thenReturn(room);
        when(likesRepository.findByRoomsId(roomsId)).thenReturn(Optional.empty());

        // when
        LikesResponseDto.AddLikeResponseDto response = likesService.addLike(roomsId, user);

        // then
        assertEquals(room.getName(), response.getRoomName());
        assertEquals(user.getNickName(), response.getNickName());
    }

    @Test
    @DisplayName("LikesService cancelLike Success")
    void cancelLikeSuccess() {
        // given
        Long roomsId = 1L;
        Users user = Users.builder().id(1L).nickName("TestUser").build();
        Rooms room = Rooms.builder().id(roomsId).name("TestRoom").build();

        when(roomsService.findById(anyLong())).thenReturn(room);
        when(likesRepository.findByRoomsId(roomsId)).thenReturn(Optional.of(Likes.builder().build()));

        // when
        LikesResponseDto.DeleteLikeResponseDto result = likesService.cancelLike(roomsId, user);

        // then
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(user.getNickName(), result.getNickName());

    }

    @Test
    @DisplayName("LikesService cancelLike UserNotLikedException")
    void cancelLikeUserNotLiked() {
        // given
        Long roomsId = 1L;
        Users user = Users.builder().id(1L).nickName("TestUser").build();
        Rooms room = Rooms.builder().id(roomsId).name("TestRoom").build();

        when(roomsService.findById(anyLong())).thenReturn(room);
        when(likesRepository.findByRoomsId(roomsId)).thenReturn(Optional.empty());

        // when & then
        UserNotLikedException exception = assertThrows(UserNotLikedException.class, () -> {
            likesService.cancelLike(roomsId, user);
        });

        assertEquals("해당 사용자가 좋아요를 누르지 않았습니다.", exception.getMessage());
        assertEquals(LikesExceptionCode.USER_NOT_LIKED.getErrorCode(), exception.getErrorCode());

        verify(roomsService, times(1)).findById(anyLong());
        verify(likesRepository, times(1)).findByRoomsId(roomsId);
        verify(likesRepository, never()).deleteByRoomsId(anyLong()); // deleteByRoomsId 메소드는 호출되지 않아야 함
    }

    private Rooms createRooms(){
        return Rooms.builder()
                .name("Test Room")
                .address("TestAddress")
                .build();
    }

    private Users createUsers(){
        return Users.builder()
                .nickName("TestUser")
                .build();
    }
}
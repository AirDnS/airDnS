package com.example.airdns.like;

import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.service.LikesService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikesServiceTest {

    @InjectMocks
    private LikesService likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private RoomsService roomsService;

    @Test
    @DisplayName("LikesService Rooms Like Success")
    public void RoomLikesSuccess() {
        // given
        Rooms room = createRooms();
        Users user = createUsers();

        when(roomsService.findRooms(anyLong())).thenReturn(room);
        when(likesRepository.save(any(Likes.class))).thenReturn(null);

        // when
        LikesResponseDto.AddLikeResponseDto result = likesService.postLike(1L, user);

        // then
        assertNotNull(result);
        assertEquals("Test Room", result.getRoomName());
        assertEquals("TestUser", result.getNickName());

        verify(roomsService, times(1)).findRooms(anyLong());
        verify(likesRepository, times(1)).save(any(Likes.class));
    }

    @Test
    @DisplayName("LikesService Room delete Likes Success")
    public void deleteRoomLikesSuccess() {
        // given
        Rooms room = createRooms();
        Users user = createUsers();
        // when
        when(roomsService.findRooms(anyLong())).thenReturn(room);
        doNothing().when(likesRepository).deleteByRoomsId(anyLong());
        LikesResponseDto.DeleteLikeResponseDto result = likesService.deleteLike(1L, user);

        // then
        assertNotNull(result);
        assertEquals("Test Room", result.getRoomName());
        assertEquals("TestUser", result.getNickName());
        verify(roomsService, times(1)).findRooms(anyLong());
        verify(likesRepository, times(1)).deleteByRoomsId(anyLong());
    }

    private Rooms createRooms(){
        return Rooms.builder()
                .id(1L)
                .name("Test Room")
                .build();
    }

    private Users createUsers(){
        return Users.builder()
                .id(1L)
                .nickName("TestUser")
                .build();
    }
}
package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RoomsServiceImplTest {

    @Mock
    RoomsRepository roomsRepository;

    private RoomsServiceImplV1 roomsService;

    @BeforeEach
    void setUp() {
        roomsService = new RoomsServiceImplV1(roomsRepository);
    }

    @Test
    @DisplayName("ID 조회 실패")
    void failFindById() {
        //given
        //when
        Exception exception = assertThrows(RoomsCustomException.class,
                () -> roomsService.findById(-1L)
        );

        //then
        assertEquals(RoomsExceptionCode.INVALID_ROOMS_ID.getMessage(), exception.getMessage());

    }

}
package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Disabled
class RoomsServiceTest {

    @Autowired
    RoomsRepository roomsRepository;

    @Autowired
    RoomsService roomsService;

    @Test
    @DisplayName("방 번호 조회")
    void failFindById() {
        //given
        //when
        Rooms rooms = roomsService.findById(1L);

        //then
        assertEquals(1L, (long) rooms.getId());

    }

}
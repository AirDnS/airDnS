package com.example.airdns.domain.room.service;

import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.equipment.service.EquipmentsService;
import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.image.service.ImagesService;
import com.example.airdns.domain.room.constant.RoomsTestConstant;
import com.example.airdns.domain.room.converter.RoomsConverter;
import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.roomequipment.entity.RoomEquipments;
import com.example.airdns.domain.roomequipment.service.RoomEquipmentsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.awss3.S3FileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RoomsServiceImplTest extends RoomsTestConstant {

    @Mock RoomsRepository roomsRepository;
    @Mock ImagesService imagesService;
    @Mock RoomEquipmentsService roomEquipmentsService;
    @Mock EquipmentsService equipmentsService;
    @Mock S3FileUtil s3FileUtil;

    @InjectMocks
    private RoomsServiceImplV1 roomsService;

    @Test
    @DisplayName("스터디 룸 등록")
    void createRooms() throws IOException {
        //given
        List<MultipartFile> files = makeFiles();

        Rooms rooms = RoomsConverter.toEntity(REQUEST_DTO, TEST_HOST);
        RoomEquipments roomEquipments = RoomEquipments.builder()
                .id(1L)
                .equipments(Equipments.builder().build())
                .build();

        when(roomsRepository.save(any(Rooms.class))).thenReturn(rooms);
        when(roomEquipmentsService.createRoomEquipments(any(Rooms.class), any())).thenReturn(roomEquipments);
        when(imagesService.createImages(any(Rooms.class), any())).thenReturn(Images.builder().build());

        //when
        RoomsResponseDto.ReadRoomsResponseDto responseDto = roomsService.createRooms(REQUEST_DTO, files, TEST_HOST);

        //then
        verify(roomsRepository).save(any(Rooms.class));

        assertEquals(responseDto.getName(), REQUEST_DTO.getName());

    }

    @Test
    @DisplayName("스터디 룸 등록 실패 - 권한없음")
    void createRoomsFailRole() {
        // given
        List<MultipartFile> files = new ArrayList<>();

        // when-then
        assertThrows(RoomsCustomException.class,
                () -> roomsService.createRooms(REQUEST_DTO, files, TEST_USER),
                RoomsExceptionCode.NO_PERMISSION_USER.getMessage());
    }

    @Test
    void readRooms() {
    }

    @Test
    void readRoomsList() {
    }

    @Test
    void updateRooms() {
    }

    @Test
    void addRoomsImages() {
    }

    @Test
    void deleteRooms() {
    }

    @Test
    void isClosed() {
    }

    @Test
    void isDeleted() {
    }

    @Test
    void findById() {
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


    private List<MultipartFile> makeFiles() throws IOException {
        String fileName = "uploadTestImg";
        MultipartFile testFile = new MockMultipartFile(fileName, new ByteArrayInputStream(new byte[]{}));

        return new ArrayList<>(List.of(new MultipartFile[]{testFile, testFile}));
    }
}
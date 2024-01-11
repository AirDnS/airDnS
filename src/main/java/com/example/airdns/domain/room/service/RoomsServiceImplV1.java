package com.example.airdns.domain.room.service;

import com.example.airdns.domain.equipment.service.EquipmentsService;
import com.example.airdns.domain.image.repository.ImagesRepository;
import com.example.airdns.domain.image.service.ImagesService;
import com.example.airdns.domain.room.converter.RoomsConverter;
import com.example.airdns.domain.room.dto.RoomsRequestDto.*;
import com.example.airdns.domain.room.dto.RoomsResponseDto.*;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.roomequipment.entity.RoomEquipments;
import com.example.airdns.domain.roomequipment.service.RoomEquipmentsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.awss3.S3FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomsServiceImplV1 implements RoomsService {

    private final RoomsRepository roomsRepository;

    private final ImagesService imagesService;

    private final RoomEquipmentsService roomEquipmentsService;

    private final EquipmentsService equipmentsService;

    private final S3FileUtil s3FileUtil;

    @Override
    public void createRooms(CreateRoomsRequestDto requestDto, List<MultipartFile> files, Users users) {
        Rooms rooms = RoomsConverter.toEntity(requestDto, users);
        roomsRepository.save(rooms);

        for (Long equipment : requestDto.getEquipment()) {
            roomEquipmentsService.createRoomEquipments(
                    rooms, equipmentsService.findById(equipment)
            );
        }

        for(MultipartFile file : files) {
            //TODO 롤백 시 이미지 제거 (선택1: 롤백 로직 추가, 선택2: 배치 시스템 구성)
            String fileUrl = s3FileUtil.uploadFile(file, rooms.getId() + "_");
            imagesService.createImages(rooms, fileUrl);
        }

    }

    @Override
    public ReadRoomsResponseDto readRooms(Long roomsId, Users users) {
        return null;
    }

    @Override
    public List<ReadRoomsResponseDto> readRoomsList(ReadRoomsListRequestDto requestDto) {
        return null;
    }

    @Override
    public UpdateRoomsResponseDto updateRooms(UpdateRoomsRequestDto requestDto, Long rooms_id, Users users) {
        return null;
    }

    @Override
    public void addRoomsImages(UpdateRoomsImagesRequestDto requestDto, Long rooms_id, Users users) {

    }

    @Override
    public void deleteRooms(Long roomsId, Users users) {

    }

    @Override
    public boolean isClosed(Long roomsId) {
        return findById(roomsId).getIsClosed();
    }

    @Override
    public boolean isDeleted(Long roomsId) {
        return findById(roomsId).getIsDeleted();
    }

    @Override
    public Rooms findById(Long roomsId) {
        return roomsRepository.findById(roomsId)
                .orElseThrow(() -> new RoomsCustomException(RoomsExceptionCode.INVALID_ROOMS_ID));
    }
}

package com.example.airdns.domain.room.servicefacade;

import com.example.airdns.domain.equipment.service.EquipmentsService;
import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.image.service.ImagesService;
import com.example.airdns.domain.reservation.service.ReservationService;
import com.example.airdns.domain.restschedule.service.RestScheduleService;
import com.example.airdns.domain.room.converter.RoomsConverter;
import com.example.airdns.domain.room.dto.RoomsRequestDto.*;
import com.example.airdns.domain.room.dto.RoomsResponseDto.ReadRoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsResponseDto.UpdateRoomsImagesResponseDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.roomequipment.service.RoomEquipmentsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomsServiceFacadeImplV1 implements RoomsServiceFacade {

    private final RoomsService roomsService;
    private final ImagesService imagesService;
    private final RoomEquipmentsService roomEquipmentsService;
    private final RestScheduleService restScheduleService;
    private final EquipmentsService equipmentsService;
    private final ReservationService reservationService;

    @Override
    public ReadRoomsResponseDto createRooms(
            CreateRoomsRequestDto requestDto,
            List<MultipartFile> files,
            Users users) {
        if (users.getRole() != UserRole.HOST && users.getRole() != UserRole.ADMIN) {
            throw new RoomsCustomException(RoomsExceptionCode.NO_PERMISSION_USER);
        }

        Rooms rooms = RoomsConverter.toEntity(requestDto, users);
        roomsService.save(rooms);

        updateEquipments(rooms, requestDto.getEquipment());
        uploadImages(rooms, files);

        return RoomsConverter.toDto(rooms);
    }

    @Override
    public ReadRoomsResponseDto readRooms(Long roomsId) {
        return RoomsConverter.toDto(roomsService.findById(roomsId));
    }

    @Override
    public Page<ReadRoomsResponseDto> readRoomsList(
            Pageable pageable,
            ReadRoomsListRequestDto requestDto) {
        return roomsService.findAllSearchFilter(
                pageable, RoomsConverter.toRoomsSearchCondition(requestDto));
    }

    @Override
    public Page<ReadRoomsResponseDto> readRoomsListByHost(
            Pageable pageable,
            ReadRoomsListByHostRequestDto requestDto,
            Users users) {
        if (users.getRole() != UserRole.HOST && users.getRole() != UserRole.ADMIN) {
            throw new RoomsCustomException(RoomsExceptionCode.NO_PERMISSION_USER);
        }

        return roomsService.findAllByHost(pageable, RoomsConverter.toRoomsSearchCondition(requestDto, users));
    }

    @Transactional
    @Override
    public ReadRoomsResponseDto updateRooms(
            UpdateRoomsRequestDto requestDto,
            Long roomsId,
            Users users) {
        Rooms rooms = roomsService.findById(roomsId);

        validateUserIsRoomsHost(rooms, users);

        rooms.resetEquipments();
        updateEquipments(rooms, requestDto.getEquipment());

        rooms.updateRooms(
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getAddress(),
                requestDto.getSize(),
                requestDto.getDesc()
        );

        return RoomsConverter.toDto(rooms);
    }

    @Transactional
    @Override
    public void updateRoomsIsClosed(
            UpdateRoomsIsClosedRequestDto requestDto,
            Long roomsId,
            Users users) {
        Rooms rooms = roomsService.findById(roomsId);

        validateUserIsRoomsHost(rooms, users);

        rooms.updateIsClosed(requestDto.getIsClosed());
    }

    @Transactional
    @Override
    public UpdateRoomsImagesResponseDto updateRoomsImages(
            UpdateRoomsImagesRequestDto requestDto,
            Long roomsId,
            List<MultipartFile> files,
            Users users) {
        Rooms rooms = roomsService.findById(roomsId);

        validateUserIsRoomsHost(rooms, users);

        if (requestDto != null) {
            deleteImage(rooms, requestDto.getRemoveImages());
        }

        if (files != null) {
            uploadImages(rooms, files);
        }

        return RoomsConverter.toImagesDto(rooms);

    }

    @Override
    public void deleteRooms(Long roomsId, Users users) {
        Rooms rooms = roomsService.findById(roomsId);

        validateUserIsRoomsHost(rooms, users);

        roomsService.delete(rooms);
    }

    @Transactional
    @Override
    public void CreateRoomsRestSchedule(
            CreateRoomsRestScheduleRequestDto requestDto,
            Long roomsId,
            Users users) {
        Rooms rooms = roomsService.findById(roomsId);

        validateUserIsRoomsHost(rooms, users);

        if (reservationService.isReserved(rooms, requestDto.getStartDate(), requestDto.getEndDate())) {
            throw new RoomsCustomException(RoomsExceptionCode.EXIST_RESERVATION);
        }

        rooms.addRestSchedule(
                restScheduleService.createRestSchedule(
                        rooms, requestDto.getStartDate(), requestDto.getEndDate()
                )
        );
    }

    @Transactional
    @Override
    public void DeleteRoomsRestSchedule(
            DeleteRoomsRestScheduleRequestDto requestDto,
            Long roomsId,
            Users users) {

        Rooms rooms = roomsService.findById(roomsId);

        validateUserIsRoomsHost(rooms, users);

        restScheduleService.deleteRestSchedule(requestDto.getRestScheduleId(), rooms);
    }

    private void validateUserIsRoomsHost(Rooms rooms, Users users) {
        if (!rooms.getUsers().getId().equals(users.getId())) {
            throw new RoomsCustomException(RoomsExceptionCode.NO_PERMISSION_USER);
        }
    }

    private void updateEquipments(Rooms rooms, List<Long> equipments) {
        equipments.stream().distinct().forEach(
                equipment -> rooms.addEquipments(
                        roomEquipmentsService.createRoomEquipments(
                                rooms, equipmentsService.findById(equipment)
                        )
                )
        );
    }

    private void uploadImages(Rooms rooms, List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (file == null) continue;

            //TODO 롤백 시 이미지 제거 (선택1: 롤백 로직 추가, 선택2: 배치 시스템 구성)
            rooms.addImage(imagesService.createImages(rooms, file));
        }
    }

    private void deleteImage(Rooms rooms, List<Long> removeImages) {
        if (!rooms.getImagesList().stream().map(Images::getId).toList()
                .containsAll(removeImages)) {
            throw new RoomsCustomException(RoomsExceptionCode.IMAGES_NOT_EXIST);
        }

        removeImages.forEach(images -> imagesService.deleteImages(images, rooms));
    }
}

package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.dto.RoomsRequestDto.*;
import com.example.airdns.domain.room.dto.RoomsResponseDto.*;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoomsServiceImplV1 implements RoomsService{

    private final RoomsRepository roomsRepository;

    @Override
    public void createRooms(CreateRoomsRequestDto requestDto, Users users) {

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

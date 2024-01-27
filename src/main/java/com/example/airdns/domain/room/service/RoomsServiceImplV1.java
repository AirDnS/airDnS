package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.converter.RoomsConverter;
import com.example.airdns.domain.room.dto.RoomsResponseDto.ReadRoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomsServiceImplV1 implements RoomsService {

    private final RoomsRepository roomsRepository;

    @Override
    public Rooms save(Rooms rooms) {
        return roomsRepository.save(rooms);
    }

    @Override
    public void delete(Rooms rooms) {
        roomsRepository.delete(rooms);
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

    @Override
    public Page<ReadRoomsResponseDto> findAllSearchFilter(Pageable pageable, RoomsSearchConditionDto roomsSearchCondition) {
        return roomsRepository.findAllSearchFilter(pageable, roomsSearchCondition);
    }
}

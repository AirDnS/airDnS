package com.example.airdns.domain.room.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoServiceImpl;
import com.example.airdns.domain.room.dto.RoomsResponseDto.ReadRoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.QRooms;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.room.repository.RoomsRepositoryQuery;
import com.example.airdns.domain.room.repository.RoomsRepositoryQueryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomsServiceImplV1 implements RoomsService {

    private final RoomsRepository roomsRepository;
    private final RoomsRepositoryQueryImpl roomsRepositoryQuery;
    private final DeleteInfoServiceImpl deleteInfoService;

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
        return roomsRepository.findByIdAndIsDeletedFalse(roomsId)
                .orElseThrow(() -> new RoomsCustomException(RoomsExceptionCode.INVALID_ROOMS_ID));
    }

    @Override
    public Page<ReadRoomsResponseDto> findAllSearchFilter(Pageable pageable, RoomsSearchConditionDto condition) {
        return roomsRepository.findAllSearchFilter(pageable, condition);
    }

    @Override
    public Page<ReadRoomsResponseDto> findAllByHost(Pageable pageable, RoomsSearchConditionDto condition) {
        return roomsRepository.findAllByHost(pageable, condition);
    }

    @Override
    public List<Long> findDeletedRoomIds(QRooms qRooms, Long userId){
        return roomsRepositoryQuery.findDeletedRoomIds(qRooms, userId);
    }
    public void saveDeletedRoomInfo(Long roomsId){
        Rooms room = findById(roomsId);
        deleteInfoService.saveDeletedRoomsInfo(room);
    }

    @Override
    public void deleteByUserId(QRooms qRooms, Long userId){
        roomsRepositoryQuery.deleteByUserId(qRooms, userId);
    }
}

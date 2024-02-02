package com.example.airdns.domain.room.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoServiceImpl;
import com.example.airdns.domain.payment.service.PaymentService;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.domain.reservation.service.ReservationService;
import com.example.airdns.domain.reservation.service.ReservationServiceImplV1;
import com.example.airdns.domain.room.dto.RoomsResponseDto.ReadRoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsResponseDto.ReadRoomsListResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomsServiceImplV1 implements RoomsService {

    private final RoomsRepository roomsRepository;
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
    public Rooms findByIdAndIsDeletedTrue(Long roomId){
        return roomsRepository.findByIdAndIsDeletedTrue(roomId)
                .orElseThrow(() -> new RoomsCustomException(RoomsExceptionCode.INVALID_ROOMS_ID));
    }
    @Override
    public List<ReadRoomsListResponseDto> findAllSearchFilter(RoomsSearchConditionDto condition) {
        return roomsRepository.findAllSearchFilter(condition);
    }

    @Override
    public Page<ReadRoomsResponseDto> findAllByHost(Pageable pageable, RoomsSearchConditionDto condition) {
        return roomsRepository.findAllByHost(pageable, condition);
    }

    @Override
    public List<Long> findRoomIdsByUserId(Long userId){
        return roomsRepository.findRoomIdsByUserId(userId);
    }

    @Transactional
    public void saveDeletedRoomInfo(Long roomId){
        Rooms room = roomsRepository.findById(roomId).orElseThrow(
                // 삭제된 Room
                ()-> new RoomsCustomException(RoomsExceptionCode.INVALID_ROOMS_ID)
        );
        Rooms saveRoom = roomsRepository.findDeleteRoomInfo(room);
        deleteInfoService.saveDeletedRoomsInfo(saveRoom);
    }

    @Override
    public void deleteByUserId(Long userId){
        roomsRepository.deleteByUserId(userId);
    }

    @Override
    public List<Long> findRoomIds(LocalDateTime deleteTime){
        return roomsRepository.findRoomIds(deleteTime);
    }

    @Override
    public void deleteRoomInfo(Long roomId){
        roomsRepository.deleteRoomInfo(roomId);
    }
}

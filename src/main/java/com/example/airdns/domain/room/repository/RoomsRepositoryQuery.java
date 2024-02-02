package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomsRepositoryQuery {
    List<RoomsResponseDto.ReadRoomsListResponseDto> findAllSearchFilter(
            RoomsSearchConditionDto condition);

    Page<RoomsResponseDto.ReadRoomsResponseDto> findAllByHost(
            Pageable pageable, RoomsSearchConditionDto roomsSearchCondition);

    List<Long> findRoomIdsByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Long> findRoomIds(LocalDateTime deleteTime);

    void deleteRoomInfo(Long roomId);

    Rooms findDeleteRoomInfo(Rooms room);
}

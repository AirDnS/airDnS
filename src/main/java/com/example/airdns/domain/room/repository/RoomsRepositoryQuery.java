package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.QRooms;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomsRepositoryQuery {
    Page<RoomsResponseDto.ReadRoomsResponseDto> findAllSearchFilter(
            Pageable pageable, RoomsSearchConditionDto condition);

    Page<RoomsResponseDto.ReadRoomsResponseDto> findAllByHost(
            Pageable pageable, RoomsSearchConditionDto roomsSearchCondition);

    List<Long> findDeletedRoomIds(QRooms qRooms, Long userId);

    void deleteByUserId(QRooms qRooms, Long userId);
}

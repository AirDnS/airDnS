package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomsRepositoryQuery {
    Page<RoomsResponseDto.ReadRoomsResponseDto> findAllSearchFilter(
            Pageable pageable, RoomsSearchConditionDto condition);
}

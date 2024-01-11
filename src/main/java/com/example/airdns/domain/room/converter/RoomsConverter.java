package com.example.airdns.domain.room.converter;

import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;

public class RoomsConverter {

    public static Rooms toEntity(RoomsRequestDto.CreateRoomsRequestDto requestDto, Users users) {
        return Rooms.builder()
                .users(users)
                .price(requestDto.getPrice())
                .address(requestDto.getAddress())
                .size(requestDto.getSize())
                .description(requestDto.getDesc())
                .name(requestDto.getName())
                .build();
    }
}

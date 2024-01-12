package com.example.airdns.domain.room.converter;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;

import java.util.List;
import java.util.stream.Collectors;

public class RoomsConverter {

    public static Rooms toEntity(RoomsRequestDto.ReadRoomsRequestDto requestDto, Users users) {
        return Rooms.builder()
                .users(users)
                .price(requestDto.getPrice())
                .address(requestDto.getAddress())
                .size(requestDto.getSize())
                .description(requestDto.getDesc())
                .name(requestDto.getName())
                .build();
    }

    public static RoomsResponseDto.ReadRoomsResponseDto toDto(Rooms rooms) {
        return RoomsResponseDto.ReadRoomsResponseDto.builder()
                .name(rooms.getName())
                .price(rooms.getPrice())
                .address(rooms.getAddress())
                .size(rooms.getSize())
                .desc(rooms.getDescription())
                .equipment(rooms.getRoomEquipmentsList().stream()
                        .map((roomEquipments) -> roomEquipments.getEquipments().getId())
                        .collect(Collectors.toList())
                )
                .imageUrl(rooms.getImagesList().stream()
                        .map((Images::getImageUrl))
                        .collect(Collectors.toList())
                )
                .build();
    }
}

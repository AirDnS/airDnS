package com.example.airdns.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class RoomsResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsResponseDto {
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private String desc;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectRoomsResponseDto {
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private String desc;
        private List<Long> equipment;
        private List<String> imageUrl;
    }
}

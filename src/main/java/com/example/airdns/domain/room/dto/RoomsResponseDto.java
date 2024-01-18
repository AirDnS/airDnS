package com.example.airdns.domain.room.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RoomsResponseDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsResponseDto {
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private String desc;
        private List<Long> equipment;
        private List<String> imageUrl;
        private List<List<LocalDateTime>> reservatedTimeList;
    }

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
    public static class UpdateRoomsImagesResponseDto {
        private List<String> imageUrl;
    }

}

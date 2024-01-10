package com.example.airdns.domain.room.dto;

import com.example.airdns.domain.room.entity.Rooms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RoomsRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddRoomsRequestDto {
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private String desc;
        private List<Integer> equipment;
        private List<String> imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class selectRoomsListRequestDto {
        private String name;
        private String desc;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsRequestDto {
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private String desc;
        private Boolean isClosed;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsImagesRequestDto {
        private List<String> imageUrls;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateRoomsEquipmentsRequestDto {
        private String name;
        private String desc;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addRoomsHolidaysRequestDto {
        private LocalDate date;
    }

}

package com.example.airdns.domain.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    public static class ReadRoomsRequestDto {
        @Schema(description = "방 이름", example = "room name")
        private String name;

        @Schema(description = "방 가격", example = "500000")
        private BigDecimal price;

        @Schema(description = "주소", example = "서울특별시 강남구 테헤란로44길 8")
        private String address;

        @Schema(description = "방 평수", example = "10")
        private Integer size;

        @Schema(description = "방 설명", example = "크기가 아담한 방입니다")
        private String desc;

        @Schema(description = "방에 입력할 장비 종류 리스트", example = "[1,2,3]")
        private List<Long> equipment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsListRequestDto {
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
    public static class UpdateRoomsEquipmentsRequestDto {
        private String name;
        private String desc;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRoomsHolidaysRequestDto {
        private LocalDate date;
    }

}

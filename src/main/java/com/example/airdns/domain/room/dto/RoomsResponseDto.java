package com.example.airdns.domain.room.dto;

import com.example.airdns.domain.image.dto.ImagesResponseDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class RoomsResponseDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsResponseDto {
        private Long roomsId;
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private String desc;
        private Boolean isClosed;
        private LocalDateTime createdAt;
        private List<Map<String, Object>> equipment;
        private List<ImagesResponseDto.ReadImagesResponseDto> image;
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

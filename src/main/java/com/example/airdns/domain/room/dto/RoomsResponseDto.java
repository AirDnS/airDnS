package com.example.airdns.domain.room.dto;

import com.example.airdns.domain.image.dto.ImagesResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
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
        private List<List<LocalDateTime>> restScheduleList;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsListResponseDto {
        private Long roomsId;
        private String name;
        private BigDecimal price;
        private String address;
        private Integer size;
        private Boolean isClosed;
        private LocalDateTime createdAt;
        private String image;
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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "휴식 일정 응답 dto")
    public static class ReadRoomsRestScheduleResponseDto {
        private Long id;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }

}

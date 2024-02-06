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
        @Schema(description = "방 ID", example = "5")
        private Long roomsId;

        @Schema(description = "이름", example = "앨리스의 방")
        private String name;

        @Schema(description = "가격", example = "10_000")
        private BigDecimal price;

        @Schema(description = "주소", example = "서울특별시 서초구 서초대로77길 54")
        private String address;

        @Schema(description = "평수", example = "4")
        private Integer size;

        @Schema(description = "상세 설명", example = "이 방은 이상한 나라에 있는 방입니다.")
        private String desc;

        @Schema(description = "활성화 여부", example = "false")
        private Boolean isClosed;

        @Schema(description = "생성 시간", example = "2024-02-06 10:00:00")
        private LocalDateTime createdAt;

        @Schema(description = "장비", example = "[1,3,4,10]")
        private List<Map<String, Object>> equipment;

        @Schema(description = "이미지", example = "[{'id': 23,'imageUrl':'https://air-dns.org/image1'}]")
        private List<ImagesResponseDto.ReadImagesResponseDto> image;

        @Schema(description = "예약된 시간", example = "['2024-02-06 10:00:00', '2024-02-06 11:00:00']")
        private List<List<LocalDateTime>> reservatedTimeList;

        @Schema(description = "후무 시간", example = "['2024-02-06 10:00:00', '2024-02-06 11:00:00']")
        private List<List<LocalDateTime>> restScheduleList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsListResponseDto {
        @Schema(description = "방 조회 목록")
        private List<ReadRoomsListContentDto> content;

        @Schema(description = "지도 검색 범위", example = "4")
        private Integer searchLevel;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsListContentDto {
        @Schema(description = "방 ID", example = "5")
        private Long roomsId;

        @Schema(description = "이름", example = "앨리스의 방")
        private String name;

        @Schema(description = "가격", example = "10_000")
        private BigDecimal price;

        @Schema(description = "주소", example = "서울특별시 서초구 서초대로77길 54")
        private String address;

        @Schema(description = "평수", example = "4")
        private Integer size;

        @Schema(description = "활성화 여부", example = "false")
        private Boolean isClosed;

        @Schema(description = "생성 시간", example = "2024-02-06 10:00:00")
        private LocalDateTime createdAt;

        @Schema(description = "이미지", example = "https://air-dns.org/image1")
        private String image;

        @Schema(description = "위도", example = "33.450701")
        private Double latitude;

        @Schema(description = "경도", example = "126.570667")
        private Double longitude;

        @Schema(description = "검색 위치로 부터의 거리", example = "23.1267")
        private Double distance;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsResponseDto {
        @Schema(description = "이름", example = "앨리스의 방")
        private String name;

        @Schema(description = "가격", example = "10_000")
        private BigDecimal price;

        @Schema(description = "주소", example = "서울특별시 서초구 서초대로77길 54")
        private String address;

        @Schema(description = "평수", example = "4")
        private Integer size;

        @Schema(description = "상세 설명", example = "이 방은 이상한 나라에 있는 방입니다.")
        private String desc;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsImagesResponseDto {
        @Schema(description = "이미지", example = "['https://air-dns.org/image1']")
        private List<String> imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "휴식 일정 응답 dto")
    public static class ReadRoomsRestScheduleResponseDto {
        @Schema(description = "휴무 일자 ID", example = "5")
        private Long id;

        @Schema(description = "시작 시간", example = "2024-02-06 10:00:00")
        private LocalDateTime startTime;

        @Schema(description = "종료 시간", example = "2024-02-06 11:00:00")
        private LocalDateTime endTime;
    }

}

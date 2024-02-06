package com.example.airdns.domain.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RoomsRequestDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRoomsRequestDto {
        @Schema(description = "방 이름", example = "room name")
        private String name;

        @Schema(description = "방 가격", example = "500000")
        @PositiveOrZero(message = "가격은 0보다 큰 값을 입력해야 합니다.")
        private BigDecimal price;

        @Schema(description = "주소", example = "서울특별시 강남구 테헤란로44길 8")
        private String address;

        @Schema(description = "상세주소", example = "[1,2,3]")
        private String addressDetail;

        @Schema(description = "방 평수", example = "10")
        @PositiveOrZero(message = "평수는 0보다 큰 값을 입력해야 합니다.")
        private Integer size;

        @Schema(description = "방 설명", example = "크기가 아담한 방입니다")
        private String desc;

        @Schema(description = "방에 입력할 장비 종류 리스트", example = "[1,2,3]")
        private List<Long> equipment;

        @Schema(description = "위도", example = "33.450701")
        private Double latitude;

        @Schema(description = "경도", example = "126.570667")
        private Double longitude;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsRequestDto {
        @Schema(description = "방 이름", example = "군부대가 보이는 강의실")
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
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsListRequestDto {
        @Schema(description = "커서", example = "1L")
        private Long cursor;

        @Schema(description = "페이지 크기", example = "10")
        private Long pageSize;

        @Schema(description = "검색어", example = "엘리스")
        private String keyword;

        @Schema(description = "가격 범위", example = "[1,50]")
        @Size(min=2, max=2, message = "가격 검색은 최솟값, 최댓값으로 구성되어야 합니다.")
        private List<BigDecimal> price;

        @Schema(description = "크기 범위", example = "[4,10]")
        @Size(min=2, max=2, message = "크기 검색은 최솟값, 최댓값으로 구성되어야 합니다.")
        private List<Integer> size;

        @Schema(description = "장비 종류", example = "[1,2,4,5,10]")
        private List<Long> equipment;

        @Schema(description = "검색 위도", example = "33.450701")
        private Double latitude; // 위도

        @Schema(description = "검색 경도", example = "126.570667")
        private Double longitude; // 경도

        @Schema(description = "검색 지도 범위", example = "4")
        private Integer searchLevel;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadRoomsListByHostRequestDto {
        @Schema(description = "검색어", example = "엘리스")
        private String keyword;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsRequestDto {
        @Schema(description = "변경할 이름", example = "해변이 보이는 강의실")
        private String name;

        @Schema(description = "가격", example = "10_0000")
        private BigDecimal price;

        @Schema(description = "주소", example = "조선민주주의인민공화국의 직할시")
        private String address;

        @Schema(description = "크기", example = "10")
        private Integer size;

        @Schema(description = "방 설명", example = "작고 불편한데 싼 방입니다.")
        private String desc;

        @Schema(description = "장비 종류 리스트", example = "[1,2,3]")
        private List<Long> equipment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsImagesRequestDto {
        @Schema(description = "삭제할 이미지 ID", example = "[1,2,3]")
        private List<Long> removeImages;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsIsClosedRequestDto {
        @Schema(description = "방 운영 여부", example = "false")
        @NotNull(message = "방 운영 여부를 선택해주세요")
        private Boolean isClosed;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRoomsRestScheduleRequestDto {
        @Schema(description = "휴식 일정 시작 시간", example = "2023-01-02 12:00")
        @NotNull(message = "시작 시간을 입력해주세요.")
        private LocalDateTime startDate;

        @Schema(description = "휴식 일정 종료 시간", example = "2023-01-02 12:30")
        @NotNull(message = "종료 시간을 입력해주세요.")
        private LocalDateTime endDate;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteRoomsRestScheduleRequestDto {
        @Schema(description = "삭제할 휴식 일정 번호", example = "[2,4]")
        @NotNull(message = "삭제할 휴식 일정 번호를 입력해주세요.")
        private Long restScheduleId;
    }

}

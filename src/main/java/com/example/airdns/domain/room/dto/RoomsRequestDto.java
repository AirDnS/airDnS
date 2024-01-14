package com.example.airdns.domain.room.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

        @Schema(description = "방에 입력할 장비 종류 리스트", example = "[1,2,3]")
        private List<Long> equipment;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRoomsImagesRequestDto {
        private List<Long> removeImages;
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
    public static class CreateRoomsRestScheduleRequestDto {
        @NotNull(message = "시작 시간을 입력해주세요.")
        private LocalDateTime startDate;

        @NotNull(message = "종료 시간을 입력해주세요.")
        private LocalDateTime endDate;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteRoomsRestScheduleRequestDto {
        @NotNull(message = "삭제할 휴식 일정 번호를 입력해주세요.")
        private Long restScheduleId;
    }
}

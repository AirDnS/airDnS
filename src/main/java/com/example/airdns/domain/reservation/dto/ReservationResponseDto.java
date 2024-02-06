package com.example.airdns.domain.reservation.dto;

import com.example.airdns.domain.reservation.entity.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Create Reservation Response")
    public static class CreateReservationResponseDto implements Serializable {

        @Schema(description = "예약 ID", example = "4")
        private Long id;

        @Schema(description = "예약 총 가격", example = "1000.00")
        private BigDecimal price;

        @Schema(description = "예약명", example = "test,2024-05-18T18:00,2024-05-18T20:00")
        private String reservationName;

        @Schema(description = "유저 이메일", example = "test@naver.com")
        private String userEmail;

        @Schema(description = "유저 네임", example = "홍길동")
        private String userName;

        @Schema(description = "유저 핸드폰 번호", example = "010-1234-1234")
        private String userContact;

        public static CreateReservationResponseDto from(Reservation reservation) {
            return CreateReservationResponseDto.builder()
                    .id(reservation.getId())
                    .price(reservation.getPrice())
                    .reservationName(reservation.getName())
                    .userEmail(reservation.getUsers().getEmail())
                    .userName(reservation.getUsers().getName())
                    .userContact(reservation.getUsers().getContact())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Read Reservation Response")
    public static class ReadReservationResponseDto implements Serializable {

        @Schema(description = "예약 ID", example = "4")
        private Long id;

        @Schema(description = "방 이름", example = "집중이 잘되는 방")
        private String roomName;

        @Schema(description = "방 주소", example = "경남 거창군 거창읍 거열로 148")
        private String address;

        @Schema(description = "체크인 시간", example = "2024-01-31 06:00:00")
        private LocalDateTime checkIn;

        @Schema(description = "예약 취소 여부", example = "false")
        private Boolean isCancelled;

        @Schema(description = "체크아웃 시간", example = "2024-01-31 10:00:00")
        private LocalDateTime checkOut;

        @Schema(description = "방 ID", example = "10")
        private Long roomsId;


        public static ReadReservationResponseDto from(Reservation reservation) {
            return ReadReservationResponseDto.builder()
                    .id(reservation.getId())
                    .roomName(reservation.getRooms().getName())
                    .address(reservation.getRooms().getAddress())
                    .isCancelled(reservation.getIsCancelled())
                    .checkIn(reservation.getCheckIn())
                    .checkOut(reservation.getCheckOut())
                    .roomsId(reservation.getRooms().getId())
                    .build();
        }
    }
}

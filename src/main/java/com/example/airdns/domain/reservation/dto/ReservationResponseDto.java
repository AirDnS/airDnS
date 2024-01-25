package com.example.airdns.domain.reservation.dto;

import com.example.airdns.domain.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReservationResponseDto implements Serializable {
        private Long id;

        private LocalDateTime checkIn;

        private LocalDateTime checkOut;

        private Long roomsId;

        private Long usersId;

        public static UpdateReservationResponseDto from(Reservation reservation) {
            return UpdateReservationResponseDto.builder()
                    .id(reservation.getId())
                    .checkIn(reservation.getCheckIn())
                    .checkOut(reservation.getCheckOut())
                    .roomsId(reservation.getRooms().getId())
                    .usersId(reservation.getUsers().getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadReservationResponseDto implements Serializable {
        private Long id;

        private String roomName;

        private String address;

        private LocalDateTime checkIn;

        private LocalDateTime checkOut;

        private Long roomsId;


        public static ReadReservationResponseDto from(Reservation reservation) {
            return ReadReservationResponseDto.builder()
                    .id(reservation.getId())
                    .roomName(reservation.getRooms().getName())
                    .address(reservation.getRooms().getAddress())
                    .checkIn(reservation.getCheckIn())
                    .checkOut(reservation.getCheckOut())
                    .roomsId(reservation.getRooms().getId())
                    .build();
        }
    }

}

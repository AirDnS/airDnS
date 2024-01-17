package com.example.airdns.domain.reservation.dto;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
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

        public static UpdateReservationResponseDto of(Reservation reservation) {
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

        private LocalDateTime checkIn;

        private LocalDateTime checkOut;

        private Long roomsId;


        public static ReadReservationResponseDto from(Reservation reservation) {
            return ReadReservationResponseDto.builder()
                    .id(reservation.getId())
                    .checkIn(reservation.getCheckIn())
                    .checkOut(reservation.getCheckOut())
                    .roomsId(reservation.getRooms().getId())
                    .build();
        }
    }

}

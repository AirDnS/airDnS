package com.example.airdns.domain.reservation.dto;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReservationRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Create Reservation request")
    public static class CreateReservationDto {

        @Schema(description = "checkInTime", example = "2024-05-10 10:00:00")
        private LocalDateTime checkInTime;

        @Schema(description = "checkOutTime", example = "2024-05-10 12:00:00")
        private LocalDateTime checkOutTime;

        public Reservation toEntity(Users users, Rooms rooms) {
            return Reservation.builder()
                    .users(users)
                    .rooms(rooms)
                    .checkIn(checkInTime)
                    .checkOut(checkOutTime)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Create Reservation request")
    public static class UpdateReservationDto {

        @Schema(description = "checkInTime", example = "2024-05-10T10:00:00")
        private LocalDateTime checkInTime;

        @Schema(description = "checkOutTime", example = "2024-05-10T12:00:00")
        private LocalDateTime checkOutTime;

    }
}

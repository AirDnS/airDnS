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
    public static class ReservationResponse implements Serializable {
        private Long id;

        private LocalDateTime checkIn;

        private LocalDateTime checkOut;

        private LocalDate reservedAt;

        private Boolean isDeleted;

        private LocalDateTime deletedAt;

        private Rooms rooms;

        private Users users;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        // 단건 조회 Response 에 맞게 수정할 예정
        public static ReservationResponse from(Reservation reservation) {
            return ReservationResponse.builder()
                    .id(reservation.getId())
                    .checkIn(reservation.getCheckIn())
                    .checkOut(reservation.getCheckOut())
                    .reservedAt(reservation.getReservedAt())
                    .isDeleted(reservation.getIsDeleted())
                    .deletedAt(reservation.getDeletedAt())
                    .createdAt(reservation.getCreatedAt())
                    .modifiedAt(reservation.getModifiedAt())
                    .build();
        }



    }

}

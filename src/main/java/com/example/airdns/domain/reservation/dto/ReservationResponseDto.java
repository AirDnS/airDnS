package com.example.airdns.domain.reservation.dto;

import com.example.airdns.domain.reservation.entity.Reservation;
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
    public static class CreateReservationResponseDto implements Serializable {
        private Long id;

        private BigDecimal price;

        private String reservationName;

        private String userEmail;

        private String userName;

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
    public static class ReadReservationResponseDto implements Serializable {
        private Long id;

        private String roomName;

        private String address;

        private LocalDateTime checkIn;

        private Boolean isCancelled;

        private LocalDateTime checkOut;

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

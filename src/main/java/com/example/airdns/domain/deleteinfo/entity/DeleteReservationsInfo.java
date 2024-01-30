package com.example.airdns.domain.deleteinfo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deleteReservationInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteReservationsInfo {

    // 공통 DeleteInfo column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime cancelledAt;

    // Reservation DeleteInfo column
    @Column
    private LocalDateTime checkIn;

    @Column
    private LocalDateTime checkOut;

    // @Column
    private String roomName;

    // 예약자 성함
    // @Column
    private String reserverName;
}

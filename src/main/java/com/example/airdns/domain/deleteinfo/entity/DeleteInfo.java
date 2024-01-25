package com.example.airdns.domain.deleteinfo.entity;

import com.example.airdns.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deleteInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteInfo {

    // 공통 DeleteInfo column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;

    private LocalDateTime deletedAt;

    // User DeleteInfo column
    private String address;

    private String contact;

    private String email;

    private String nickname;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Room DeleteInfo column
    // + address
    private String roomName;

    private BigDecimal roomPrice;

    private String roomDescription;

    private Integer roomSize;

    // Reservation DeleteInfo column
    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    // Payment DeleteInfo column
    private String failReason;

}

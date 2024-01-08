package com.example.airdns.domain.reservation.entity;


import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservaion")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE reservation SET deleted = true WHERE id = ?")
public class Reservation extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isReserved;

    @Column
    private LocalDateTime checkIn;

    @Column
    private LocalDateTime checkOut;

    @Column
    private LocalDateTime reservedAt;

    @Column
    private Boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rooms_id")
    private Rooms rooms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

}

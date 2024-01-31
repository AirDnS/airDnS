package com.example.airdns.domain.deleteinfo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deleteRoomsInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteRoomsInfo {

    // 공통 DeleteInfo column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime deletedAt;

    // Room DeleteInfo column
    // + address
    @Column
    private String name;

    @Column
    private String address;

    @Column
    private BigDecimal price;

    @Column
    private String description;

    @Column
    private Integer size;

    // 룸 소유자
    @Column
    private String owner;
}

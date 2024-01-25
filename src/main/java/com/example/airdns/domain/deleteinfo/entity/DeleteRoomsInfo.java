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

    private LocalDateTime deletedAt;

    // Room DeleteInfo column
    // + address
    private String name;

    private String address;

    private BigDecimal price;

    private String description;

    private Integer size;

    // 룸 소유자
    private String owner;
}

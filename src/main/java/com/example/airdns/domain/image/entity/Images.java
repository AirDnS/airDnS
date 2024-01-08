package com.example.airdns.domain.image.entity;


import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Images extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rooms_id")
    private Rooms rooms;

}

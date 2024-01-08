package com.example.airdns.domain.equipment.entity;


import com.example.airdns.domain.equipmentcategory.entity.EquipmentCategories;
import com.example.airdns.domain.roomequipment.entity.RoomEquipments;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipments")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Equipments extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_categories_id")
    private EquipmentCategories equipmentCategories;

    @Builder.Default
    @OneToMany(mappedBy = "equipments", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEquipments> roomEquipments = new ArrayList<>();

}

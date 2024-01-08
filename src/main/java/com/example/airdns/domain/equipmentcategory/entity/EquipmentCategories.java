package com.example.airdns.domain.equipmentcategory.entity;

import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment_categories")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EquipmentCategories extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "equipmentCategories", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipments> equipmentsList = new ArrayList<>();
}

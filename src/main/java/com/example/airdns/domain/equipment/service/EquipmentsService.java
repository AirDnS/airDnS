package com.example.airdns.domain.equipment.service;

import com.example.airdns.domain.equipment.entity.Equipments;

import java.util.List;
import java.util.Map;

public interface EquipmentsService {

    Equipments createEquipments();

    List<Map<String, Object>> readEquipmentsList();

    Equipments findById(Long equipments_id);
}

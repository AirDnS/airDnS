package com.example.airdns.domain.equipment.service;

import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.equipment.repository.EquipmentsRepository;
import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.entity.Rooms;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipmentsServiceImplV1 implements EquipmentsService {

    private final EquipmentsRepository equipmentsRepository;

    @Override
    public Equipments createEquipments() {
        return equipmentsRepository.save(Equipments.builder()
                .build());
    }
}

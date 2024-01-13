package com.example.airdns.domain.equipment.service;

import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.equipment.exception.EquipmentsCustomException;
import com.example.airdns.domain.equipment.exception.EquipmentsExceptionCode;
import com.example.airdns.domain.equipment.repository.EquipmentsRepository;
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

    @Override
    public Equipments findById(Long equipments_id) {
        return equipmentsRepository.findById(equipments_id)
                .orElseThrow(() -> new EquipmentsCustomException(EquipmentsExceptionCode.INVALID_EQUIPMENTS_ID));
    }
}

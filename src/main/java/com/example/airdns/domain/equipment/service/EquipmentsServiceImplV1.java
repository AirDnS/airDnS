package com.example.airdns.domain.equipment.service;

import com.example.airdns.domain.equipment.dto.EquipmentsResponseDto;
import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.equipment.exception.EquipmentsCustomException;
import com.example.airdns.domain.equipment.exception.EquipmentsExceptionCode;
import com.example.airdns.domain.equipment.repository.EquipmentsRepository;
import com.example.airdns.domain.equipmentcategory.service.EquipmentCategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EquipmentsServiceImplV1 implements EquipmentsService {

    private final EquipmentsRepository equipmentsRepository;
    private final EquipmentCategoriesService equipmentCategoriesService;

    @Override
    public Equipments createEquipments() {
        return equipmentsRepository.save(Equipments.builder()
                .build());
    }

    @Override
    public List<Map<String, Object>> readEquipmentsList() {
        List<Map<String, Object>> returnList = new ArrayList<>(); //클라이언트 요청 형태대로 내려줌

        // map (CategoryId, equipment) 생성
        Map<Long, List<EquipmentsResponseDto.ReadEquipmentsResponseDto>> equipmentsMap = new HashMap<>();

        equipmentsRepository.findAll()
                .forEach(equipments -> {
                    Long categoryId = equipments.getEquipmentCategories().getId();
                    List<EquipmentsResponseDto.ReadEquipmentsResponseDto> responseList =
                            equipmentsMap.getOrDefault(categoryId, new ArrayList<>());

                    responseList.add(
                            EquipmentsResponseDto.ReadEquipmentsResponseDto.builder()
                                    .id(equipments.getId())
                                    .name(equipments.getName())
                                    .build()
                    );

                    equipmentsMap.put(categoryId, responseList);
                });

        // 카테고리 순서대로 정렬
        equipmentCategoriesService.findAll()
                .forEach(category -> {
                    Map<String, Object> categoryResponseMap = new HashMap<>();
                    categoryResponseMap.put("label", category.getName());
                    categoryResponseMap.put("options", equipmentsMap.get(category.getId()));
                    returnList.add(categoryResponseMap);
                });

        return returnList;
    }

    @Override
    public Equipments findById(Long equipments_id) {
        return equipmentsRepository.findById(equipments_id)
                .orElseThrow(() -> new EquipmentsCustomException(EquipmentsExceptionCode.INVALID_EQUIPMENTS_ID));
    }

}

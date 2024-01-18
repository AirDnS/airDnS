package com.example.airdns.domain.equipmentcategory.service;

import com.example.airdns.domain.equipmentcategory.entity.EquipmentCategories;
import com.example.airdns.domain.equipmentcategory.repository.EquipmentCategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipmentCategoriesServiceImplV1 implements EquipmentCategoriesService {

    private final EquipmentCategoriesRepository equipmentCategoriesRepository;

    @Override
    public List<EquipmentCategories> findAll() {
        return equipmentCategoriesRepository.findAll();
    }

}

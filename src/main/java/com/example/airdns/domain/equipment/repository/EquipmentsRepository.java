package com.example.airdns.domain.equipment.repository;

import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.image.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentsRepository extends JpaRepository<Equipments, Long> {

}

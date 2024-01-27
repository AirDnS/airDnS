package com.example.airdns.domain.deleteinfo.repository;

import com.example.airdns.domain.deleteinfo.entity.DeleteReservationsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteReservationInfoRepository extends JpaRepository<DeleteReservationsInfo, Long> {
}

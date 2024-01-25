package com.example.airdns.domain.deleteinfo.repository;

import com.example.airdns.domain.deleteinfo.entity.DeleteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteInfoRepository extends JpaRepository<DeleteInfo, Long> {
}

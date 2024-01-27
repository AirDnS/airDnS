package com.example.airdns.domain.deleteinfo.repository;

import com.example.airdns.domain.deleteinfo.entity.DeleteRoomsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteUsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteRoomsInfoRepository extends JpaRepository<DeleteRoomsInfo, Long> {
}
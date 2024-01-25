package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    List<Payments> findByIsDeletedTrueAndDeletedAtBefore(LocalDateTime deleteTime);
}
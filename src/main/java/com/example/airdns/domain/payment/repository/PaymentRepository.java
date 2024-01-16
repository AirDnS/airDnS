package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

}
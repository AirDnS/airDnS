package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryQuery {

    Optional<Payment> findByReservationIdAndId(Long reservationId, Long paymentId);

    List<Payment> findByReservationId(Long reservationId);

    Optional<Payment> findByReservationIdAndIdAndIsDeletedFalse(Long reservationId, Long paymentId);
}

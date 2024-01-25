package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Object> findByPaymentId(Long paymentId);

    Optional<Payment> findByReservationId(Long reservationId);
}

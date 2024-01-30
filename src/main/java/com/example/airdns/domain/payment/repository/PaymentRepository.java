package com.example.airdns.domain.payment.repository;

import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.room.repository.RoomsRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryQuery {

    Optional<Payment> findByReservationIdAndIdAndIsDeletedFalse(Long reservationId, Long paymentId);

}

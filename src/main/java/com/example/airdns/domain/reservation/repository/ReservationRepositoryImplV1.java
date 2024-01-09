package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepositoryImplV1 extends JpaRepository<Reservation, Long> , ReservationRepository {
}

package com.example.airdns.domain.reservation.repository;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByUsersId(Long userId);

    Optional<Reservation> findFirstByRoomsAndIsCancelledFalseAndCheckInBeforeAndCheckOutAfter(Rooms rooms, LocalDateTime checkOut, LocalDateTime checkIn);

    Optional<Reservation> findByIdAndIsCancelledFalse(Long reservationId);
}

package com.example.airdns.domain.payment.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepositoryQuery {
    List<Long> findPaymentIdsByUserId(Long userId);
    void deleteByUserId(Long userId);
    List<Long> findPaymentIdsByReservationIds(List<Long> reservationIds);
    void deleteByRoomId(Long roomId);
    void deleteByReservationId(Long reservationId);
    List<Long> findPaymentIdsByReservationId(Long reservationId);
    List<Long> findPaymentIdsByDeleteTime(LocalDateTime deleteTime);
    void deletePaymentId(Long paymentId);
}

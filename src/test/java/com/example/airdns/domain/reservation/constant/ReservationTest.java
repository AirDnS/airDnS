package com.example.airdns.domain.reservation.constant;

import com.example.airdns.domain.reservation.entity.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReservationTest extends CommonTest{

    Long TEST_RESERVATION_ID = 1L;

    BigDecimal TEST_RESERVATION_PRICE = BigDecimal.valueOf(10000L);

    String TEST_RESERVATION_NAME = "TEST";

    LocalDateTime TEST_CHECK_IN = LocalDateTime.now();

    LocalDateTime TEST_CHECK_OUT = LocalDateTime.now().plusHours(2L);

    Reservation TEST_RESERVATION = Reservation.builder()
            .id(TEST_RESERVATION_ID)
            .price(TEST_RESERVATION_PRICE)
            .name(TEST_RESERVATION_NAME)
            .checkIn(TEST_CHECK_IN)
            .checkOut(TEST_CHECK_OUT)
            .users(TEST_USER)
            .rooms(TEST_ROOMS)
            .build();

}

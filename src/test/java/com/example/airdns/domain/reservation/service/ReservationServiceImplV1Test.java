package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.reservation.servicefacade.ReservationServiceFacadeImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.user.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReservationServiceImplV1Test {

    @Autowired
    private ReservationServiceFacadeImplV1 reservationServiceFacadeImplV1;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    public void before() {
        Rooms rooms = Rooms.builder().
                id(1L).
                address("서울특별시").
                isDeleted(false).
                name("test").
                price(BigDecimal.valueOf(500L)).
                description("좋은 방입니다.").
                size(10).
                build();
        roomsRepository.save(rooms);
    }

    @AfterEach
    public void after() {
    }

    @Test
    @DisplayName("레디스 라이브러리 중 Reddison ")
    public void Reddions_10개의_예약() throws InterruptedException {

        // given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(threadCount);
        ReservationRequestDto.CreateReservationRequestDto createReservationRequestDto = ReservationRequestDto.
                CreateReservationRequestDto.
                builder().
                checkInTime(LocalDateTime.parse("2024-05-22T20:00:00")).
                checkOutTime(LocalDateTime.parse("2024-05-22T22:00:00")).
                build();

        // when
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    roomsRepository.findById((long)finalI + 1L);
                    reservationServiceFacadeImplV1.createReservation((long) finalI + 1L, 12L, createReservationRequestDto);
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        // then
        Reservation reservation = reservationRepository.findById(35L).orElseThrow();
        assertEquals(1L, reservation.getUsers().getId());
    }
}
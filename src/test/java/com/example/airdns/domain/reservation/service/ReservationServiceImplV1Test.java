package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoService;
import com.example.airdns.domain.reservation.constant.ReservationTest;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.room.entity.Rooms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplV1Test implements ReservationTest {

    @InjectMocks
    private ReservationServiceImplV1 reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private DeleteInfoService deleteInfoService;


    @Nested
    @DisplayName("예약 조회")
    class 예약_조회 {

        @Nested
        @DisplayName("성공 케이스")
        class 성공_케이스 {

            @Test
            @DisplayName("예약 조회 성공")
            void 성공() {
                // given
                given(reservationRepository.findByIdAndIsCancelledFalse(TEST_RESERVATION_ID))
                        .willReturn(Optional.ofNullable(TEST_RESERVATION));

                // when
                Reservation findReservation = reservationService.findById(TEST_RESERVATION_ID);

                // then
                Assertions.assertSame(TEST_RESERVATION, findReservation);
                Assertions.assertEquals(Objects.requireNonNull(TEST_RESERVATION).getId(), findReservation.getId());
                Assertions.assertEquals(TEST_RESERVATION.getName(), findReservation.getName());
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class 실패_케이스 {

            @Test
            @DisplayName("예약 조회 실패")
            void 해당_예약이_없을_때() {
                // given
                Long reservationId = 2L;
                when(reservationRepository.findByIdAndIsCancelledFalse(reservationId))
                        .thenThrow(new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION));

                // when
                Exception exception = assertThrows(ReservationCustomException.class, () -> {
                    reservationService.findById(reservationId);
                });

                // then
                Assertions.assertEquals(exception.getMessage(), "해당 예약 정보가 존재하지 않습니다.");
            }
        }
    }

    @Nested
    @DisplayName("예약 생성")
    class 예약_생성 {

        @Nested
        @DisplayName("성공 케이스")
        class 성공_케이스 {

            @Test
            @DisplayName("예약 생성 성공")
            void 성공() {
                // given
                given(reservationRepository.save(any(Reservation.class)))
                        .willReturn(TEST_RESERVATION);

                // when
                Reservation saveReservation = reservationService.save(TEST_RESERVATION);

                // then
                Assertions.assertSame(TEST_RESERVATION, saveReservation);
            }
        }
    }
    @Nested
    @DisplayName("예약 가능 여부 확인")
    class 예약_가능_여부 {

        @Nested
        @DisplayName("성공 케이스")
        class 성공_케이스 {

            @Test
            @DisplayName("예약이 있다")
            void 예약이_있다() {

                // given
                given(reservationRepository
                        .findFirstByRoomsAndIsCancelledFalseAndCheckInBeforeAndCheckOutAfter(
                                TEST_ROOMS
                                , TEST_CHECK_IN
                                , TEST_CHECK_OUT).isPresent())
                        .willReturn(true);

                // when
                Boolean isReserved = reservationService.isReserved(TEST_ROOMS, TEST_CHECK_IN, TEST_CHECK_OUT);

                // then
                Assertions.assertEquals(true, isReserved);
            }
        }
    }
}
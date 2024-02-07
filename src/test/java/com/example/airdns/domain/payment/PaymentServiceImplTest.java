package com.example.airdns.domain.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.payment.exception.PaymentCustomException;
import com.example.airdns.domain.payment.exception.PaymentExceptionCode;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private PaymentServiceImplV1 paymentService;

    @Test
    @DisplayName("결제 조회 성공 케이스")
    void readPaymentSuccess() {
        // given
        Long reservationId = 1L;
        Long paymentId = 2L;

        Reservation mockedReservation = Reservation.builder()
                .id(reservationId)
                .build();

        Payment mockedPayment = Payment.builder()
                .id(paymentId)
                .orderId("123")
                .orderName("ExOrderName123")
                .amount(10000L)
                .paymentKey("ExPaymentKey123")
                .paymentType("CARD")
                .reservation(mockedReservation)
                .build();

        when(paymentRepository.findByReservationIdAndIdAndIsDeletedFalse(reservationId, paymentId))
                .thenReturn(Optional.of(mockedPayment));

        // when
        PaymentResponseDto.ReadPaymentResponseDto responseDto = paymentService.readPayment(
                reservationId, paymentId);

        // then
        assertNotNull(responseDto);
        assertEquals(mockedPayment.getId(), responseDto.getId());
        assertEquals(mockedPayment.getOrderName(), responseDto.getOrderName());
        assertEquals(mockedPayment.getAmount(), responseDto.getAmount());
        assertEquals(mockedPayment.getPaymentType(), responseDto.getPaymentType());

        verify(paymentRepository, times(1))
                .findByReservationIdAndIdAndIsDeletedFalse(reservationId, paymentId);
    }

    @Test
    @DisplayName("결제 조회 실패 케이스 - 결제 내역이 없는 경우")
    void readPaymentFailWhenPaymentNotExist() {
        // given
        Long reservationId = 1L;
        Long paymentId = 2L;
        when(paymentRepository.findByReservationIdAndIdAndIsDeletedFalse(reservationId, paymentId))
                .thenThrow(new PaymentCustomException(PaymentExceptionCode.NOT_FOUND_MATCHED_PAYMENT));

        // when & then
        Exception exception = assertThrows(
                PaymentCustomException.class,
                () -> paymentService.readPayment(reservationId, paymentId),
                "예약번호와 일치하는 결제 내역이 존재하지 않습니다."
        );

        verify(paymentRepository, times(1))
                .findByReservationIdAndIdAndIsDeletedFalse(reservationId, paymentId);
    }

}
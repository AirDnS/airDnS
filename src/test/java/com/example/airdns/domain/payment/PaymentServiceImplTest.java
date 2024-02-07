package com.example.airdns.domain.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.domain.reservation.entity.Reservation;
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

    @InjectMocks
    private PaymentServiceImplV1 paymentService;

    @Test
    @DisplayName("PaymentService readPayment Success")
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
}
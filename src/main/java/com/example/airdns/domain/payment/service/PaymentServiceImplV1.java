package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.entity.Payments;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplV1 implements PaymentService {

    private final PaymentRepository paymentsRepository;
    private final ReservationRepository reservationRepository;
    private final RestTemplate restTemplate;

    @Value("${payment.toss.secret_api_key}")
    private String secretApiKey;

    @Value("${payment.toss.url}")
    private String tossPaymentsApiUrl;

    @Override
    public String requestPayment(Long reservationId,
            PaymentRequestDto.RequestPaymentDto requestDto) {

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            String paymentKey = requestToTossPaymentsApi(requestDto);

            Payments payments = Payments.builder()
                    .reservation(reservation)
                    .paymentKey(paymentKey)
                    .build();

            paymentsRepository.save(payments);

            return paymentKey;
        } else{
            return "Reservation Not Found";
        }
    }

    private String requestToTossPaymentsApi(PaymentRequestDto.RequestPaymentDto requestDto) {

        String authHeader = "Basic " + base64Encode(secretApiKey + ":");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);

        String orderId = requestDto.getOrderId();
        Long amount = requestDto.getAmount();
        String paymentKey = requestDto.getPaymentKey();

        JSONObject requestData = new JSONObject();
        requestData.put("orderId", orderId);
        requestData.put("amount", amount);
        requestData.put("paymentKey", paymentKey);

        // HTTP 요청
        RequestEntity<String> requestEntity = new RequestEntity<>(requestData.toJSONString(),
                headers, HttpMethod.POST, URI.create(tossPaymentsApiUrl), String.class);

        // 토스페이먼츠 API에 HTTP POST 요청 작업
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        return response.getBody();

    }

    private String base64Encode(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}

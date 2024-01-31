package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoService;
import com.example.airdns.domain.deleteinfo.service.DeleteInfoServiceImpl;
import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.payment.entity.QPayment;
import com.example.airdns.domain.payment.exception.PaymentCustomException;
import com.example.airdns.domain.payment.exception.PaymentExceptionCode;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import com.example.airdns.domain.payment.repository.PaymentRepositoryQuery;
import com.example.airdns.domain.payment.repository.PaymentRepositoryQueryImpl;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.reservation.service.ReservationService;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.airdns.domain.room.entity.Rooms;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j(topic = "토스페이먼츠 API 요청")
@RequiredArgsConstructor
public class PaymentServiceImplV1 implements PaymentService {

    private final ReservationService reservationService;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final RestTemplate restTemplate;
    private final DeleteInfoService deleteInfoService;

    @Value("${payment.toss.url}")
    private String tossPaymentApiUrl;

    @Value("${payment.toss.secret_api_key}")
    private String secretApiKey;

    @Override
    public PaymentResponseDto.CreatePaymentResponseDto createPayment(
            Long userId,
            Long reservationId,
            PaymentRequestDto.CreatePaymentRequestDto requestDto) {

        Reservation reservation = reservationService.findById(reservationId);

        if (!Objects.equals(reservation.getUsers().getId(), userId)) {
            throw new PaymentCustomException(PaymentExceptionCode.FORBIDDEN_RESERVATION_NOT_USER);
        }

        try {
            String authorizations = encodeSecretKey(secretApiKey);

            HttpHeaders headers = createHttpHeaders(authorizations);

            JSONObject requestData = createTossApiRequestBody(requestDto);

            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(requestData, headers);

            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(
                    tossPaymentApiUrl, requestEntity, JSONObject.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return handleSuccessfulResponse(requestDto, reservation);
            } else {
                handlePaymentError(String.valueOf(responseEntity));
                throw new RuntimeException("Error requesting payment");
            }
        } catch (Exception e) {
            log.error("Error requesting payment", e);
            throw new RuntimeException("Error requesting payment");
        }
    }

    private String encodeSecretKey(String secretKey) throws UnsupportedEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((secretKey + ":").getBytes("UTF-8"));
        log.info(secretKey);
        return "Basic " + new String(encodedBytes, StandardCharsets.UTF_8);

    }

    private HttpHeaders createHttpHeaders(String authorizations) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, authorizations);
        return headers;
    }

    private JSONObject createTossApiRequestBody(
            PaymentRequestDto.CreatePaymentRequestDto requestDto) {
        JSONObject requestData = new JSONObject();
        requestData.put("orderId", requestDto.getOrderId());
        requestData.put("amount", requestDto.getAmount());
        requestData.put("paymentKey", requestDto.getPaymentKey());
        requestData.put("orderName", requestDto.getOrderName());
        requestData.put("paymentType", requestDto.getPaymentType());
        return requestData;
    }

    private PaymentResponseDto.CreatePaymentResponseDto handleSuccessfulResponse(
            PaymentRequestDto.CreatePaymentRequestDto requestDto
            , Reservation reservation) {
        // 성공 시 결제 정보 및 예약정보 저장
        Payment payment = Payment.builder()
                .orderId(requestDto.getOrderId())
                .orderName(requestDto.getOrderName())
                .amount(requestDto.getAmount())
                .paymentKey(requestDto.getPaymentKey())
                .paymentType(requestDto.getPaymentType())
                .reservation(reservation)
                .build();
        paymentRepository.save(payment);
        return PaymentResponseDto.CreatePaymentResponseDto.from(payment);
    }

    private void handlePaymentError(String errorResponse) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject errorObject = (JSONObject) parser.parse(errorResponse);

            String errorCode = (String) errorObject.get("code");
            String errorMessage = (String) errorObject.get("message");

            if ("NOT_FOUND_PAYMENT_SESSION".equals(errorCode)) {
            } else {
                log.error("Unhandled Toss Payment Error: {} - {}", errorCode, errorMessage);
            }
        } catch (ParseException e) {
            log.error("Error parsing error response", e);
        }
    }

    @Override
    public PaymentResponseDto.ReadPaymentResponseDto readPayment(Long reservationId,
            Long paymentId) {
        Payment payment = paymentRepository.findByReservationIdAndIdAndIsDeletedFalse(reservationId, paymentId)
                .orElseThrow(() -> new PaymentCustomException(
                        PaymentExceptionCode.NOT_FOUND_MATCHED_RESERVATION));

        return PaymentResponseDto.ReadPaymentResponseDto.from(payment);
    }

    @Override
    public List<Long> findPaymentIdsByUserId(Long userId){
        return paymentRepository.findPaymentIdsByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId){
        paymentRepository.deleteByUserId(userId);
    }

    @Override
    public void saveDeletedPaymentInfo(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                ()-> new PaymentCustomException(PaymentExceptionCode.NOT_FOUND_PAYMENT)
        );
        deleteInfoService.saveDeletedPaymentInfo(payment);
    }

    @Override
    public List<Long> findPaymentIdsByReservationIds(List<Long> reservationIds){
        return paymentRepository.findPaymentIdsByReservationIds(reservationIds);
    }

    @Override
    public void deleteByRoomId(Long roomId){
        paymentRepository.deleteByRoomId(roomId);
    }

    @Override
    public void deleteByReservationId(Long reservationId){
        paymentRepository.deleteByReservationId(reservationId);
    }

    @Override
    public List<Long> findPaymentIdsByReservationId(Long reservationId){
        return paymentRepository.findPaymentIdsByReservationId(reservationId);
    }

    @Override
    public void deletePayment(LocalDateTime deleteTime){
        List<Long> paymentIds = paymentRepository.findPaymentIdsByDeleteTime(deleteTime);

        for (Long paymentId : paymentIds) {
            // DeleteInfo 저장
            saveDeletePaymentInfo(paymentId);

            // Payments 삭제
            paymentRepository.deletePaymentId(paymentId);
        }
    }

    private void saveDeletePaymentInfo(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                ()-> new PaymentCustomException(PaymentExceptionCode.NOT_FOUND_PAYMENT)
        );
        deleteInfoService.saveDeletedPaymentInfo(payment);
    }
}

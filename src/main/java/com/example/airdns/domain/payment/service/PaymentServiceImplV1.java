package com.example.airdns.domain.payment.service;

import com.example.airdns.domain.payment.dto.PaymentRequestDto;
import com.example.airdns.domain.payment.dto.PaymentResponseDto;
import com.example.airdns.domain.payment.entity.Payments;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    private final PaymentRepository paymentsRepository;
    private final RestTemplate restTemplate;

    @Value("${payment.toss.url}")
    private String tossPaymentsApiUrl;

    @Value("${payment.toss.secret_api_key}")
    private String secretApiKey;

    @Override
    public PaymentResponseDto requestPayment(PaymentRequestDto.RequestPaymentDto requestDto) {
        try {
            String authorizations = encodeSecretKey(secretApiKey);

            HttpHeaders headers = createHttpHeaders(authorizations);

            JSONObject requestData = createTossApiRequestBody(requestDto);

            HttpEntity<JSONObject> requestEntity = new HttpEntity<>(requestData, headers);

            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(tossPaymentsApiUrl, requestEntity, JSONObject.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return handleSuccessfulResponse(requestDto, responseEntity.getBody());
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

    private JSONObject createTossApiRequestBody(PaymentRequestDto.RequestPaymentDto requestDto) {
        JSONObject requestData = new JSONObject();
        requestData.put("orderId", requestDto.getOrderId());
        requestData.put("amount", requestDto.getAmount());
        requestData.put("paymentKey", requestDto.getPaymentKey());
        return requestData;
    }

    private PaymentResponseDto handleSuccessfulResponse(PaymentRequestDto.RequestPaymentDto requestDto, JSONObject responseBody) {
        // 성공 시 결제 정보 저장
        savePaymentInfo((String) responseBody.get("paymentKey"), requestDto, responseBody);

        return PaymentResponseDto.builder()
                .paymentKey((String) responseBody.get("paymentKey"))
                .orderId(requestDto.getOrderId())
                .amount(requestDto.getAmount())
                .payType((String) responseBody.get("payType"))
                .createdAt((String) responseBody.get("createdAt"))
                .build();
    }

    private void savePaymentInfo(String paymentKey, PaymentRequestDto.RequestPaymentDto requestDto, JSONObject responseBody) {
        Payments payments = Payments.builder()
                .orderId(requestDto.getOrderId())
                .amount(requestDto.getAmount())
                .paymentKey(paymentKey)
                .build();

        paymentsRepository.save(payments);
    }

    private void handlePaymentError(String errorResponse) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject errorObject = (JSONObject) parser.parse(errorResponse);

            String errorCode = (String) errorObject.get("code");
            String errorMessage = (String) errorObject.get("message");

            if ("NOT_FOUND_PAYMENT_SESSION".equals(errorCode)) {
                log.error("NOT_FOUND_PAYMENT_SESSION Error: {}", errorMessage);
            } else {
                log.error("Unhandled Toss Payment Error: {} - {}", errorCode, errorMessage);
            }
        } catch (ParseException e) {
            log.error("Error parsing error response", e);
        }
    }
}


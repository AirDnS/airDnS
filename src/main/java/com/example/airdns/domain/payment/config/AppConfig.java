package com.example.airdns.domain.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

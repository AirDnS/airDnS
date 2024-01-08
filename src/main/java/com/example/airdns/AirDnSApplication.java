package com.example.airdns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AirDnSApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirDnSApplication.class, args);
    }

}

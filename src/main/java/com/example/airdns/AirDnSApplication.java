package com.example.airdns;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@EnableJpaRepositories(basePackages = {
        "com.example.airdns.domain.reservation.repository"
        ,"com.example.airdns.domain.equipment.repository"
        ,"com.example.airdns.domain.equipmentcategory.repository"
        ,"com.example.airdns.domain.image.repository"
        ,"com.example.airdns.domain.like.repository"
        ,"com.example.airdns.domain.payment.repository"
        ,"com.example.airdns.domain.restschedule.repository"
        ,"com.example.airdns.domain.review.repository"
        ,"com.example.airdns.domain.room.repository"
        ,"com.example.airdns.domain.roomequipment.repository"
        ,"com.example.airdns.domain.user.repository"})
@SpringBootApplication
@EnableJpaAuditing
public class AirDnSApplication {

    @PostConstruct
    public void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(AirDnSApplication.class, args);
    }

}

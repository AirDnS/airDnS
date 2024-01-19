package com.example.airdns.domain.equipment.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EquipmentsResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadEquipmentsResponseDto {
        private Long id;
        private String name;
    }

}

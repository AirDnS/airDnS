package com.example.airdns.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RoomsSearchConditionDto {
    String keyword;
    BigDecimal startPrice;
    BigDecimal endPrice;
    Integer startSize;
    Integer endSize;
    List<Long> equpmentList;
}

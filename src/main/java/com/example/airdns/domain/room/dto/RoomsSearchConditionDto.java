package com.example.airdns.domain.room.dto;

import com.example.airdns.domain.user.entity.Users;
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
    Users users;
    Boolean isClosed;
}

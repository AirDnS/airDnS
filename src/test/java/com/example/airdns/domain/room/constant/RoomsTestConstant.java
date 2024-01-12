package com.example.airdns.domain.room.constant;

import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomsTestConstant {

    protected final String TEST_NAME = "방 이름";
    protected BigDecimal TEST_PRICE = BigDecimal.valueOf(100000L);
    protected String TEST_DESCRIPTION = "방 설명";
    protected List<Long> TEST_EQUIPMENT = new ArrayList<>(List.of(new Long[]{1L, 2L}));
    protected List<String> TEST_IMAGEURL = new ArrayList<>(List.of(new String[]{"testImageUrl"}));

    protected Users TEST_USER = Users.builder()
            .nickName("유저 이름")
            .email("user@test.email")
            .role(UserRole.USER)
            .build();

    protected Users TEST_HOST = Users.builder()
            .nickName("호스트 이름")
            .email("host@test.email")
            .role(UserRole.HOST)
            .build();

    protected Rooms testRooms = Rooms.builder()
            .name(TEST_NAME)
            .users(TEST_HOST)
            .description(TEST_DESCRIPTION)
            .price(TEST_PRICE)
            .build();


    protected RoomsRequestDto.CreateRoomsRequestDto REQUEST_DTO
            = RoomsRequestDto.CreateRoomsRequestDto.builder()
            .name(TEST_NAME)
            .desc(TEST_DESCRIPTION)
            .price(TEST_PRICE)
            .equipment(TEST_EQUIPMENT)
            .build();
}

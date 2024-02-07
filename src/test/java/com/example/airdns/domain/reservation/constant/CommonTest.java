package com.example.airdns.domain.reservation.constant;

import com.example.airdns.domain.oauth2.common.OAuth2Provider;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface CommonTest {

    Long TEST_USER_ID = 1L;

    String TEST_USER_EMAIL = "test@naver.com";

    String TEST_USER_NICKNAME = "test";

    OAuth2Provider TEST_USER_OAUTH_PROVIDER = OAuth2Provider.KAKAO;

    UserRole TEST_USER_ROLE = UserRole.USER;

    Users TEST_USER = Users.builder()
            .id(TEST_USER_ID)
            .email(TEST_USER_EMAIL)
            .nickname(TEST_USER_NICKNAME)
            .provider(TEST_USER_OAUTH_PROVIDER)
            .role(TEST_USER_ROLE)
            .build();

    Long TEST_HOST_ID = 2L;

    String TEST_HOST_EMAIL = "testHost@naver.com";

    String TEST_HOST_NICKNAME = "testHost";

    OAuth2Provider TEST_HOST_OAUTH_PROVIDER = OAuth2Provider.KAKAO;

    UserRole TEST_HOST_ROLE = UserRole.HOST;

    Users TEST_HOST = Users.builder()
            .id(TEST_HOST_ID)
            .email(TEST_HOST_EMAIL)
            .nickname(TEST_HOST_NICKNAME)
            .provider(TEST_HOST_OAUTH_PROVIDER)
            .role(TEST_HOST_ROLE)
            .build();

    Long TEST_ROOM_ID = 1L;

    String TEST_ROOM_NAME = "앨리스의 방";

    BigDecimal TEST_ROOM_PRICE = BigDecimal.valueOf(1000L);

    String TEST_ROOM_ADDRESS = "서울특별시";

    Integer TEST_ROOM_SIZE = 4;

    String TEST_ROOM_DESC = "테스트 용입니다.";

    Rooms TEST_ROOMS = Rooms.builder()
            .id(TEST_ROOM_ID)
            .name(TEST_ROOM_NAME)
            .price(TEST_ROOM_PRICE)
            .address(TEST_ROOM_ADDRESS)
            .size(TEST_ROOM_SIZE)
            .description(TEST_ROOM_DESC)
            .build();

}

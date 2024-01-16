package com.example.airdns.domain.oauth2.common;

import com.example.airdns.domain.user.entity.Users;

import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getNickname();

    Users toEntity();

    String makeNickname();
}

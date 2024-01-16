package com.example.airdns.domain.oauth2.social;

import com.example.airdns.domain.oauth2.common.OAuth2Provider;
import com.example.airdns.domain.oauth2.common.OAuth2UserInfo;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String email;
    private final String nickName;

    public KakaoOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        // attributes 맵의 kakao_account 키의 값에 실제 attributes 맵이 할당되어 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        this.attributes = kakaoProfile;

        this.id = ((Long) attributes.get("id")).toString();
        this.email = (String) kakaoAccount.get("email");
        this.nickName = (String) attributes.get("nickname");

        this.attributes.put("nickname",nickName);
        this.attributes.put("id", id);
        this.attributes.put("email", email);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public Users toEntity() {
        return Users.builder()
                .email(email)
                .nickname(makeNickname())
                .provider(OAuth2Provider.KAKAO)
                .role(UserRole.USER)
                .build();
    }

    @Override
    public String makeNickname() {
        List<String> adjective = Arrays.asList("행복한", "슬픈", "게으른", "슬기로운", "수줍은",
                "그리운", "더러운", "섹시한", "배고픈", "배부른", "부자", "재벌", "웃고있는", "깨발랄한");
        String[] splitEmail = email.split("@");
        String name = splitEmail[0];
        String number = (int)(Math.random() * 99)+1 +"";
        Collections.shuffle(adjective);
        String adj = adjective.get(0);
        return adj+name+number;
    }
}

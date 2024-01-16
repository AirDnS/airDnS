package com.example.airdns.domain.oauth2.common;

import com.example.airdns.domain.oauth2.common.OAuth2Provider;
import com.example.airdns.domain.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.example.airdns.domain.oauth2.social.KakaoOAuth2UserUnlink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2UserUnlinkManager {

    private final KakaoOAuth2UserUnlink kakaoOAuth2UserUnlink;

    public void unlink(OAuth2Provider provider, String accessToken) {
        if (OAuth2Provider.KAKAO.equals(provider)) {
            kakaoOAuth2UserUnlink.unlink(accessToken);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Unlink with " + provider.getRegistrationId() + " is not supported");
        }
    }
}

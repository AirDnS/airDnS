package com.example.airdns.domain.oauth2.service;

import com.example.airdns.domain.oauth2.common.OAuth2UserInfo;
import com.example.airdns.domain.oauth2.common.OAuth2UserInfoFactory;
import com.example.airdns.domain.oauth2.common.OAuth2UserPrincipal;
import com.example.airdns.domain.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
                accessToken,
                oAuth2User.getAttributes());
        // OAuth2UserInfo field value validation
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        saveOrUpdate(oAuth2UserInfo);

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }

    private Users saveOrUpdate(OAuth2UserInfo oAuth2UserInfo) {
        Users users = usersRepository.findByEmail(oAuth2UserInfo.getEmail())
                .map(entity -> entity.update(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider()))
                .orElse(oAuth2UserInfo.toEntity());

        return usersRepository.save(users);
    }

}

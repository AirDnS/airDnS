package com.example.airdns.domain.oauth2.handler;

import com.example.airdns.domain.oauth2.common.OAuth2Provider;
import com.example.airdns.domain.oauth2.common.OAuth2UserInfo;
import com.example.airdns.domain.oauth2.common.OAuth2UserPrincipal;
import com.example.airdns.domain.oauth2.common.OAuth2UserUnlinkManager;
import com.example.airdns.domain.oauth2.dto.ResponseTokenDto;
import com.example.airdns.domain.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.repository.UsersRepository;
import com.example.airdns.global.cookie.CookieUtil;
import com.example.airdns.global.jwt.JwtUtil;
import com.example.airdns.global.redis.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.example.airdns.domain.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.example.airdns.domain.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final JwtUtil jwtUtil;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final RedisService redisService;
    private final UsersRepository usersRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtil.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("status", "Failed")
                    .build().toUriString();
        }

        if ("login".equalsIgnoreCase(mode)) {

            ResponseTokenDto tokenDto = createTokenDto(authentication);
            Users user = createOrUpdateUser(principal.getUserInfo());
            jwtUtil.saveRefreshToken(principal.getName(), tokenDto.getRefreshToken());

            jwtUtil.addJwtToCookie(tokenDto, response);

            return loginResponseTargetUrl(targetUrl, user);

        } else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            oAuth2UserUnlinkManager.unlink(provider, accessToken);
            redisService.deleteValues(principal.getName());

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("status", "logout")
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("status", "Failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request,
                                                 HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

    }

    private String loginResponseTargetUrl(String targetUrl, Users user){
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("status", "Success")
                .queryParam("userId", user.getId())
                .queryParam("nickname", URLEncoder.encode(user.getNickname(), StandardCharsets.UTF_8))
                .queryParam("address", URLEncoder.encode(user.getAddress(), StandardCharsets.UTF_8))
                .queryParam("contact", URLEncoder.encode(user.getContact(), StandardCharsets.UTF_8))
                .queryParam("name", URLEncoder.encode(user.getName(), StandardCharsets.UTF_8))
                .queryParam("role", user.getRole())
                .build().toUriString();
    }

    private Users createOrUpdateUser(OAuth2UserInfo oAuth2UserInfo) {
        Users users = usersRepository.findByEmail(oAuth2UserInfo.getEmail())
                .map(entity -> entity.update(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider()))
                .orElse(oAuth2UserInfo.toEntity());

        return usersRepository.save(users);
    }

    private ResponseTokenDto createTokenDto(Authentication authentication) {
        String accessToken = jwtUtil.createAccessToken(authentication);
        String refreshToken = jwtUtil.createRefreshToken(authentication);
        return ResponseTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

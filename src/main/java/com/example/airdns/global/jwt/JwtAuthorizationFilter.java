package com.example.airdns.global.jwt;

import com.example.airdns.global.exception.GlobalExceptionCode;
import com.example.airdns.global.exception.JwtCustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.airdns.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getTokenFromRequestCookie(request, JwtUtil.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(accessToken)) {
            String tokenValue = jwtUtil.substringToken(accessToken);
            JwtStatus jwtStatus = jwtUtil.validateToken(tokenValue);
            switch (jwtStatus) {
                case FAIL -> throw new JwtCustomException(GlobalExceptionCode.INVALID_TOKEN_VALUE);
                case ACCESS -> successValidatedToken(tokenValue);
                case EXPIRED -> checkRefreshToken(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Access Token 성공시 , user 가 로그아웃일 경우 체크
    private void successValidatedToken(String tokenValue) {
        // redis에서 해당 email을 refresh Token이 있는지 확인
        Authentication authentication = jwtUtil.getAuthentication(tokenValue);
        if(!refreshTokenRepository.existsByUsername(authentication.getName())){
            return;
        }
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    // Access Token 기간이 만료시 Refresh Token을 체크해야 한다.
    private void checkRefreshToken(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String refreshToken = jwtUtil.getTokenFromRequestCookie(request,JwtUtil.REFRESH_TOKEN_HEADER);
        String refreshTokenValue = jwtUtil.substringToken(refreshToken);
        JwtStatus jwtStatus = jwtUtil.validateToken(refreshTokenValue);
        switch (jwtStatus) {
            case FAIL -> throw new JwtCustomException(GlobalExceptionCode.INVALID_TOKEN_VALUE);
            case ACCESS -> makeNewAccessToken(refreshTokenValue, response);
            case EXPIRED -> throw new JwtCustomException(GlobalExceptionCode.UNAUTHORIZED_REFRESH_TOKEN_VALUE);
        }
    }

    // Refresh Token이 멀쩡할 시 새로 발급
    private void makeNewAccessToken(String tokenValue, HttpServletResponse response) throws UnsupportedEncodingException {
        Authentication authentication = jwtUtil.getAuthentication(tokenValue);
        if (refreshTokenRepository.existsByUsername(authentication.getName())) {
            String newAccessToken = jwtUtil.createAccessToken(authentication);
            String accessToken = URLEncoder.encode(newAccessToken, "utf-8").replaceAll("\\+", "%20");
            ResponseCookie accessCookie = ResponseCookie.from(AUTHORIZATION_HEADER, accessToken)
                    .domain(".air-dns.org")
                    .path("/")
                    .httpOnly(true)
                    .build();
            response.addHeader("Set-Cookie", accessCookie.toString());
        }
    }
}
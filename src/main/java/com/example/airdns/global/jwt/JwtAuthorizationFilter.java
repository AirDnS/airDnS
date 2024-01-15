package com.example.airdns.global.jwt;

import com.example.airdns.global.exception.GlobalExceptionCode;
import com.example.airdns.global.exception.JwtCustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(AUTHORIZATION_HEADER);
        String tokenValue = jwtUtil.substringToken(token);

        JwtStatus jwtStatus = jwtUtil.validateToken(tokenValue);

        switch (jwtStatus) {
            case FAIL -> throw new JwtCustomException(GlobalExceptionCode.INVALID_TOKEN_VALUE);
            case ACCESS -> successValidatedToken(tokenValue);
            case EXPIRED -> checkRefreshToken(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private void successValidatedToken(String tokenValue) {
        Authentication authentication = jwtUtil.getAuthentication(tokenValue);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void checkRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtUtil.getTokenFromRequestCookie(request);
        String tokenValue = jwtUtil.substringToken(refreshToken);
        JwtStatus jwtStatus = jwtUtil.validateToken(tokenValue);
        switch (jwtStatus) {
            case FAIL -> throw new JwtCustomException(GlobalExceptionCode.INVALID_TOKEN_VALUE);
            case ACCESS -> makeNewAccessToken(tokenValue, response);
            case EXPIRED -> throw new JwtCustomException(GlobalExceptionCode.UNAUTHORIZED_REFRESH_TOKEN_VALUE);
        }
    }

    // Refresh Token이 유효 기간이 지났을 경우
    private void makeNewAccessToken(String tokenValue, HttpServletResponse response) {
        Authentication authentication = jwtUtil.getAuthentication(tokenValue);
        if (refreshTokenRepository.existsByUsername(authentication.getName())) {
            String newAccessToken = jwtUtil.createAccessToken(authentication);
            response.addHeader(AUTHORIZATION_HEADER, newAccessToken);
        }
    }

}
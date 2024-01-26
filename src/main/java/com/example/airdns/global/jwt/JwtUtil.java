package com.example.airdns.global.jwt;

import com.example.airdns.domain.oauth2.dto.ResponseTokenDto;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    public static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 60; // One Hour
    public static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 60 * 24; // One Day

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String REFRESH_TOKEN_HEADER = "Refresh";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자(Convention)
    public static final String BEARER_PREFIX = "Bearer ";

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsServiceImpl userDetailsServieImpl;

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64URL.decode(secret);
        this.key = Keys.hmacShaKeyFor(key);
    }

    public JwtStatus validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return JwtStatus.ACCESS;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            return JwtStatus.FAIL;
        } catch (SignatureException exception) {
            return JwtStatus.FAIL;
        } catch (ExpiredJwtException exception) {
            return JwtStatus.EXPIRED;
        } catch (IllegalArgumentException exception) {
            return JwtStatus.FAIL;
        } catch (Exception exception) {
            return JwtStatus.FAIL;
        }
    }

    public String createAccessToken(Authentication authentication) {

        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(authentication.getName())
                        .setIssuedAt(date)
                        .claim(AUTHORIZATION_KEY, UserRole.USER)
                        .setExpiration(expiryDate)
                        .signWith(key, SignatureAlgorithm.HS512)
                        .compact();
    }

    public String createRefreshToken(Authentication authentication) {

        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(authentication.getName())
                        .setIssuedAt(date)
                        .claim(AUTHORIZATION_KEY, UserRole.USER)
                        .setExpiration(expiryDate)
                        .signWith(key, SignatureAlgorithm.HS512)
                        .compact();
    }


    public void saveRefreshToken(String username, String refreshToken) {
        refreshTokenRepository.saveRefreshToken(username, refreshToken);
    }

    public void addJwtToCookie(ResponseTokenDto token, HttpServletResponse res) {
        try {
            String accessToken = URLEncoder.encode(token.getAccessToken(), "utf-8").replaceAll("\\+", "%20");
            String refreshToken = URLEncoder.encode(token.getRefreshToken(), "utf-8").replaceAll("\\+", "%20");

            Cookie accessCookie = new Cookie(AUTHORIZATION_HEADER,accessToken);
            Cookie refreshCookie = new Cookie(REFRESH_TOKEN_HEADER, refreshToken);
            res.addCookie(accessCookie);
            res.addCookie(refreshCookie);
        } catch (UnsupportedEncodingException e) {
            log.error("Not Encoding");
        }
    }



    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails user = userDetailsServieImpl.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new NullPointerException("Not Found Token");
    }

    public String getTokenFromRequestCookie(HttpServletRequest req, String type) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(type)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
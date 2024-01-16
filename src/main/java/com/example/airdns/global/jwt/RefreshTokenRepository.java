package com.example.airdns.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String username, String refreshToken) {
        String value = refreshToken.substring(7);
        long time = JwtUtil.REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS;

        redisTemplate.opsForValue().set(username, value, time, TimeUnit.MILLISECONDS);
    }

    public boolean existsByUsername(String username) {

        String refreshToken = redisTemplate.opsForValue().get(username);
        return refreshToken != null && !refreshToken.isEmpty();
    }

    public void deleteRefreshToken(String username) {

        redisTemplate.delete(username);
    }
}

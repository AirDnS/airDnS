package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UserDto;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.domain.user.exception.UserCustomException;
import com.example.airdns.domain.user.exception.UserExceptionCode;
import com.example.airdns.domain.user.repository.UserRepository;
import com.example.airdns.global.jwt.JwtUtil;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import com.example.airdns.global.redis.dao.RedisDao;
import com.example.airdns.global.redis.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class KakaoService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisDao redisDao;

    public TokenDto kakaoLogin(String code) throws JsonProcessingException {

        String accessToken = getToken(code);

        log.info("토큰 : " + accessToken);

        UserDto.KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        Users kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        Authentication authentication = createAuthentication(kakaoUser);
        setAuthentication(authentication);

        TokenDto tokenDto = jwtUtil.createToken(kakaoUser.getEmail(), kakaoUser.getRole());

        Long expiration = jwtUtil.getExpiration(tokenDto.getRefreshToken());

        redisDao.setRefreshToken(kakaoUser.getEmail(), tokenDto.getRefreshToken(), expiration);

        return tokenDto;
    }

    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b210ce928f8896aa660d3e1160406f88");
        body.add("redirect_uri", "http://localhost:8080/api/users/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private UserDto.KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new UserDto.KakaoUserInfoDto(id, nickname, email);
    }


    private Users registerKakaoUserIfNeeded(UserDto.KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        System.out.println("kakaoId : " + kakaoId);
        Users kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);


        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            Users sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.builder()
                        .kakaoId(kakaoId)
                        .build();
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();

                kakaoUser = Users.builder()
                        .email(kakaoUserInfo.getEmail())
                        .password(encodedPassword)
                        .nickName(kakaoUserInfo.getNickname())
                        .role(UserRole.USER)
                        .kakaoId(kakaoId)
                        .build();
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }

    // 인증 객체 생성
    private Authentication createAuthentication(Users user) {
        UserDetailsImplV1 userDetails = new UserDetailsImplV1(user);
        return new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());
    }

    private void setAuthentication(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

}

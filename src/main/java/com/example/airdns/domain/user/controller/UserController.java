package com.example.airdns.domain.user.controller;

import com.example.airdns.domain.user.exception.UserExceptionCode;
import com.example.airdns.domain.user.service.KakaoService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.jwt.JwtUtil;
import com.example.airdns.global.redis.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final KakaoService kakaoService;
    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public String test() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/users/kakao/callback")
    public ResponseEntity<CommonResponse<String>> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        TokenDto tokenDto = kakaoService.kakaoLogin(code);
        jwtUtil.setTokenResponse(tokenDto, response);
//        token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
//        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
//        cookie.setPath("/");

//        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return ResponseEntity.ok(new CommonResponse<>(UserExceptionCode.SUCCESS_LOGIN.getHttpStatus(),UserExceptionCode.SUCCESS_LOGIN.getMessage(), "로그인"));
        //return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
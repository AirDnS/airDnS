package com.example.airdns.global.jwt;

import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.exception.JwtCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCustomExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        }
        catch (JwtCustomException e){
            response.setStatus(e.getHttpStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), new CommonResponse(e.getHttpStatus(),e.getErrorCode(), e.getMessage()));
        }
    }
}

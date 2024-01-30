package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.Users;

import java.time.LocalDateTime;

public interface UsersService {

    UsersResponseDto.UpdateUsersResponseDto updateUser(Long userId, UsersRequestDto.UpdateUserInfoRequestDto userRequestDto);
    Users findById(Long userId);

    UsersResponseDto.UpdateUsersResponseDto updateUserRole(Long userId);

    UsersResponseDto.ReadUserResponseDto getUserInfo(Long userId);
    void deleteUsers(LocalDateTime deleteTime);
    UsersResponseDto.ReadUserResponseDto readUserInfo(Long userId);
}

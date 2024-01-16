package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.Users;

public interface UsersService {

    UsersResponseDto.UpdateUsersResponseDto updateUser(Long userId, UsersRequestDto.UpdateUserInfoRequestDto userRequestDto);
    Users findById(Long userId);
}

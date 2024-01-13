package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UserDto;
import com.example.airdns.domain.user.entity.Users;

public interface UsersService {

    UserDto.UserUpdateResponseDto updateUser(Long usersId, Users user, UserDto.UserRequestDto userRequestDto);
    Users findById(Long userId);
}

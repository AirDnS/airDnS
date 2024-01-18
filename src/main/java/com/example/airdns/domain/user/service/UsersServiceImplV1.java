package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.exception.UsersExceptionCode;
import com.example.airdns.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersServiceImplV1 implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public UsersResponseDto.UpdateUsersResponseDto updateUser(Long userId
            , UsersRequestDto.UpdateUserInfoRequestDto userRequestDto) {
        Users user = findById(userId);
        user.updateInfo(userRequestDto);
        return UsersResponseDto.UpdateUsersResponseDto.of(user);
    }

    @Override
    @Transactional
    public UsersResponseDto.UpdateRoleUsersResponseDto updateUserRole(Long userId
            , UsersRequestDto.UpdateUserRoleRequestDto userRequestDto) {
        Users user = findById(userId);
        user.updateRole(userRequestDto);
        return UsersResponseDto.UpdateRoleUsersResponseDto.of(user);
    }

    @Override
    public UsersResponseDto.GetUserResponseDto getUserInfo(Long userId) {
        Users user = findById(userId);
        return UsersResponseDto.GetUserResponseDto.of(user);
    }

    @Override
    public Users findById(Long userId) {
        return usersRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(() ->
                new UsersCustomException(UsersExceptionCode.NOT_FOUND_USER));
    }



}

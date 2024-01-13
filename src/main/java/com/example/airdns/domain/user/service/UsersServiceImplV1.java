package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UserDto;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.exception.UserCustomException;
import com.example.airdns.domain.user.exception.UserExceptionCode;
import com.example.airdns.domain.user.repository.UserRepository;
import com.example.airdns.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Columns;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class UsersServiceImplV1 implements UsersService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDto.UserUpdateResponseDto updateUser(Long usersId,
                                             Users user,
                                             UserDto.UserRequestDto userRequestDto) {
        Users users = userRepository.findById(usersId).orElseThrow(null);

        users = Users.builder()
                .nickName(userRequestDto.getNickname())
                .address(userRequestDto.getAddress())
                .contact(userRequestDto.getContact())
                .build();

        userRepository.save(users);

        return UserDto.UserUpdateResponseDto.builder()
                .nickname(users.getNickName())
                .modified_at(users.getModifiedAt())
                .build();
    }

    @Override
    public Users findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserCustomException(UserExceptionCode.NOT_FOUND_USER));
    }

}

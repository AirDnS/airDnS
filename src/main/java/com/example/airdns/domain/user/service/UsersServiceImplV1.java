package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UserDto;
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

    private UsersRepository usersRepository;

    @Override
    @Transactional
    public UserDto.UserUpdateResponseDto updateUser(Long usersId,
                                             Users user,
                                             UserDto.UserRequestDto userRequestDto) {
        Users users = usersRepository.findById(usersId).orElseThrow(null);

        users = Users.builder()
                .nickName(userRequestDto.getNickname())
                .address(userRequestDto.getAddress())
                .contact(userRequestDto.getContact())
                .build();

        usersRepository.save(users);

        return UserDto.UserUpdateResponseDto.builder()
                .nickname(users.getNickName())
                .modified_at(users.getModifiedAt())
                .build();
    }

    @Override
    public Users findById(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() ->
                new UsersCustomException(UsersExceptionCode.NOT_FOUND_USER));
    }

}

package com.example.airdns.domain.user.service;

import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.Users;

import java.time.LocalDateTime;

public interface UsersService {

    /**
     * 유저 정보 수정
     * @param userId 유저 아이디
     * @param userRequestDto 유저 수정 정보
     * @return 변경된 유저 데이터
     */
    UsersResponseDto.UpdateUsersResponseDto updateUser(Long userId, UsersRequestDto.UpdateUserInfoRequestDto userRequestDto);

    /**
     * 유저 아이디 찾기
     * @param userId 유저 아이디
     * @return 유저 아이디에 대한 유저 데이터
     */
    Users findById(Long userId);

    /**
     * 유저 권한 수정
     * @param userId 유저 아이디
     * @return 유저 아이디에 대한 유저 권한 데이터
     */
    UsersResponseDto.UpdateUsersResponseDto updateUserRole(Long userId);

    /**
     * 유저 정보 조회
     * @param userId 유저 아이디
     * @return 유저 아이디에 대한 유저 조회 데이터
     */
    UsersResponseDto.ReadUserResponseDto readUserInfo(Long userId);

    /**
     * 유저 정보 삭제
     * @param deleteTime 유저 엔티티 삭제 시간
     */
    void deleteUsers(LocalDateTime deleteTime);

    void readUserSilence(Long userId);
}

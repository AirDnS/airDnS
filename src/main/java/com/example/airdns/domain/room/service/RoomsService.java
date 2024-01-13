package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.dto.RoomsRequestDto.*;
import com.example.airdns.domain.room.dto.RoomsResponseDto.*;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomsService {

    /**
     * 스터디 룸 등록
     * @param requestDto 스터디 룸 정보
     * @param files 이미지 파일
     */
    ReadRoomsResponseDto createRooms(CreateRoomsRequestDto requestDto, List<MultipartFile> files, Users users);


    /**
     * 스터디 룸 조회
     * @param roomsId 방 번호
     * @return 방 데이터
     */
    ReadRoomsResponseDto readRooms(Long roomsId);

    /**
     * 스터디 룸 전체 조회
     * @param requestDto 검색 조건
     * @return 방 리스트 데이터
     */
    List<ReadRoomsResponseDto> readRoomsList(ReadRoomsListRequestDto requestDto);

    /**
     * 스터디 룸 변경
     * @param requestDto 변경할 데이터
     * @param roomsId 방 번호
     * @param users 로그인 회원
     * @return 변경된 방 데이터
     */
    ReadRoomsResponseDto updateRooms(UpdateRoomsRequestDto requestDto, Long roomsId, Users users);

    /**
     * 스터디룸 이미지 수정
     * @param requestDto 변경된 이미지 정보
     * @param roomsId 방 번호
     * @param users 로그인 회원
     */
    UpdateRoomsImagesResponseDto updateRoomsImages(
            UpdateRoomsImagesRequestDto requestDto, Long roomsId, List<MultipartFile> files, Users users);

    /**
     * 스터디룸 삭제
     * @param roomsId 방 번호
     * @param users 로그인 회원
     */
    void deleteRooms(Long roomsId, Users users);

    /**
     * 방 활성화 여부 확인
     * @return 활성화 여부
     */
    boolean isClosed(Long roomsId);

    /**
     * 방 삭제 여부 확인
     * @return 삭제 여부
     */
    boolean isDeleted(Long roomsId);


    /**
     * 방 조회
     * @return 방
     */
    Rooms findById(Long roomsId);

}

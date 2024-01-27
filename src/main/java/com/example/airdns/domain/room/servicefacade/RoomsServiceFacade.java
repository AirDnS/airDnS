package com.example.airdns.domain.room.servicefacade;

import com.example.airdns.domain.room.dto.RoomsRequestDto.*;
import com.example.airdns.domain.room.dto.RoomsResponseDto.ReadRoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsResponseDto.UpdateRoomsImagesResponseDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomsServiceFacade {

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
    Page<ReadRoomsResponseDto> readRoomsList(Pageable pageable, ReadRoomsListRequestDto requestDto);

    /**
     * 스터디 룸 변경
     * @param requestDto 변경할 데이터
     * @param roomsId 방 번호
     * @param users 로그인 회원
     * @return 변경된 방 데이터
     */
    ReadRoomsResponseDto updateRooms(UpdateRoomsRequestDto requestDto, Long roomsId, Users users);

    /**
     * 스터디 룸 운영 여부 변경
     * @param requestDto 변경할 데이터
     * @param roomsId 방 번호
     * @param users 로그인 유저
     */
    void updateRoomsIsClosed(UpdateRoomsIsClosedRequestDto requestDto, Long roomsId, Users users);

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
     * 스터디룸 휴식 일정 등록
     * @param requestDto 휴식 일정 정보
     * @param roomsId 방 번호
     */
    void CreateRoomsRestSchedule(CreateRoomsRestScheduleRequestDto requestDto, Long roomsId, Users users);

    /**
     * 스터디룸 휴식 일정 삭제
     * @param requestDto 휴식 일정 정보
     * @param roomsId 방 번호
     */
    void DeleteRoomsRestSchedule(DeleteRoomsRestScheduleRequestDto requestDto, Long roomsId, Users users);

}

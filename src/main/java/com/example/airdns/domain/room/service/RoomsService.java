package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.dto.RoomsResponseDto.*;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomsService {

    /**
     * 방 조회
     * @return 방
     */
    Rooms findById(Long roomsId);

    /**
     * 방 저장
     * @param rooms 방 객체
     * @return 방 객체
     */
    Rooms save(Rooms rooms);

    /**
     * 방 삭제
     * @param rooms 방 객체
     */
    void delete(Rooms rooms);

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
     * 방 검색
     * @param roomsSearchCondition 방 조회 조건
     * @return 방 조회 데이터
     */
    List<ReadRoomsListResponseDto> findAllSearchFilter(RoomsSearchConditionDto roomsSearchCondition);

    /**
     * 등록한 방 검색
     * @param pageable 페이징 객체
     * @param roomsSearchCondition 방 조회 조건
     * @return 방 조회 데이터
     */
    Page<ReadRoomsResponseDto> findAllByHost(Pageable pageable, RoomsSearchConditionDto roomsSearchCondition);

    /**
     * 유저 아이디를 통한 방 삭제
     * @param userId 유저 아이디
     */
    void deleteByUserId(Long userId);

    /**
     * 삭제 시간을 통한 방 리스트 조회
     * @param deleteTime 삭제 시간
     * @return 방 아이디 리스트
     */
    List<Long> findRoomIds(LocalDateTime deleteTime);

    /**
     * 방 소프트 삭제 필드 변경
     * @param roomsId
     */
    void saveDeletedRoomInfo(Long roomsId);

    /**
     * 유저 아이디를 통한 룸 아이디 리스트 조회
     * @param userId 유저 아이디
     * @return 유저 아이디에 대한 룸 아이디 리스트
     */
    List<Long> findRoomIdsByUserId(Long userId);

    /**
     * 룸 데이터 삭제
     * @param roomId 룸 아이디
     */
    void deleteRoomInfo(Long roomId);

    /**
     * 필드 is_deleted true와 roomId에 해당 하는 방 조회
     * @param roomId
     * @return 필드 is_deleted true와 roomId에 해당 하는 방 객체 데이터
     */
    Rooms findByIdAndIsDeletedTrue(Long roomId);
}

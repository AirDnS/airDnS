package com.example.airdns.domain.room.service;

import com.example.airdns.domain.room.dto.RoomsResponseDto.*;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.QRooms;
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
     * @param pageable 페이징 객체
     * @param roomsSearchCondition 방 조회 조건
     * @return 방 조회 데이터
     */
    Page<ReadRoomsResponseDto> findAllSearchFilter(Pageable pageable, RoomsSearchConditionDto roomsSearchCondition);

    /**
     * 등록한 방 검색
     * @param pageable 페이징 객체
     * @param roomsSearchCondition 방 조회 조건
     * @return 방 조회 데이터
     */
    Page<ReadRoomsResponseDto> findAllByHost(Pageable pageable, RoomsSearchConditionDto roomsSearchCondition);
    void deleteByUserId(Long userId);
    List<Long> findRoomIds(LocalDateTime deleteTime);
    void saveDeletedRoomInfo(Long roomsId);
    List<Long> findRoomIdsByUserId(Long userId);
    void deleteRoomInfo(Long roomId);
    Rooms findByIdAndIsDeletedTrue(Long roomId);
}

package com.example.airdns.domain.restschedule.repository;

import com.example.airdns.domain.restschedule.entity.RestSchedule;
import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RestScheduleRepository extends JpaRepository<RestSchedule, Long> {

    /**
     * 시작시간과 종료시간 사이에 있는 데이터가 있는지 확인
     */
    Optional<RestSchedule> findFirstByRoomsAndStartTimeBeforeAndEndTimeAfter(Rooms rooms, LocalDateTime endDate, LocalDateTime startDate);
}

package com.example.airdns.domain.restschedule.service;

import com.example.airdns.domain.restschedule.entity.RestSchedule;
import com.example.airdns.domain.room.entity.Rooms;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface RestScheduleService {

    Page<RestSchedule> readRestSchedule(Pageable pageable, Rooms rooms);

    RestSchedule createRestSchedule(Rooms rooms, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate);

    void deleteRestSchedule(Long restscheduleId, Rooms rooms);

    boolean hasRestScheduleInRoomBetweenTimes(Rooms rooms, LocalDateTime startDate, LocalDateTime endDate);
}

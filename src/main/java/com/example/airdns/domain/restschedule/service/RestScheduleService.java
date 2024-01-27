package com.example.airdns.domain.restschedule.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.restschedule.entity.RestSchedule;
import com.example.airdns.domain.room.entity.Rooms;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public interface RestScheduleService {

    RestSchedule createRestSchedule(Rooms rooms, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate);

    void deleteRestSchedule(Long imagesId, Rooms rooms);

    boolean hasRestScheduleInRoomBetweenTimes(Rooms rooms, LocalDateTime startDate, LocalDateTime endDate);
}

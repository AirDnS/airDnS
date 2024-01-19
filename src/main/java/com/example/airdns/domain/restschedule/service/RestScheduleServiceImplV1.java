package com.example.airdns.domain.restschedule.service;

import com.example.airdns.domain.restschedule.entity.RestSchedule;
import com.example.airdns.domain.restschedule.exception.RestScheduleCustomException;
import com.example.airdns.domain.restschedule.exception.RestScheduleExceptionCode;
import com.example.airdns.domain.restschedule.repository.RestScheduleRepository;
import com.example.airdns.domain.room.entity.Rooms;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RestScheduleServiceImplV1 implements RestScheduleService {

    private final RestScheduleRepository restScheduleRepository;

    @Override
    public RestSchedule createRestSchedule(Rooms rooms, LocalDateTime startDate, LocalDateTime endDate) {

        if (!startDate.isBefore(endDate)) {
            throw new RestScheduleCustomException(RestScheduleExceptionCode.STARTTIME_IS_AFTER_ENDTIME);
        }

        if (restScheduleRepository.findFirstByEndTimeGreaterThanAndStartTimeLessThan(startDate, endDate).isPresent()) {
            throw new RestScheduleCustomException(RestScheduleExceptionCode.DUPLICATE_DATETIME);
        }

        return restScheduleRepository.save(RestSchedule.builder()
                .rooms(rooms)
                .startTime(startDate)
                .endTime(endDate)
                .build());
    }

    @Transactional
    @Override
    public void deleteRestSchedule(Long restScheduleId, Rooms rooms) {
        RestSchedule restSchedule = restScheduleRepository.findById(restScheduleId)
                .orElseThrow(() -> new RestScheduleCustomException(RestScheduleExceptionCode.INVALID_REST_SCHEDULE_ID));

        if (!rooms.getId().equals(restSchedule.getRooms().getId())) {
            throw new RestScheduleCustomException(RestScheduleExceptionCode.NO_PERMISSION_USER_REST_SCHEDULE);
        }

        restScheduleRepository.delete(restSchedule);
        rooms.deleteRestSchedule(restSchedule);
    }

}

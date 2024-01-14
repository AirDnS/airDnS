package com.example.airdns.domain.restschedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RestScheduleExceptionCode {

    NO_PERMISSION_USER_REST_SCHEDULE(HttpStatus.FORBIDDEN, "RESTSCHEDULE-001", "권한이 없습니다."),
    INVALID_REST_SCHEDULE_ID(HttpStatus.BAD_REQUEST, "RESTSCHEDULE-002", "선택한 휴무 시간이 존재하지 않습니다."),
    DUPLICATE_DATETIME(HttpStatus.BAD_REQUEST, "RESTSCHEDULE-003", "중복된 시간이 존재합니다."),
    STARTTIME_IS_AFTER_ENDTIME(HttpStatus.BAD_REQUEST, "RESTSCHEDULE-004", "종료시간은 시작시간 뒤에 나와야 합니다."),

    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
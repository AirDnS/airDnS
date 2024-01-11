package com.example.airdns.domain.reservation.service;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationServiceImplV1 implements ReservationService {

    private final UsersService usersService;
    private final RoomsService roomsService;
    private final ReservationRepository reservationRepository;

    // user와 rooms의 에러코드 완성 시 조건문 추가 예정
    // rooms 에 해당 rooms가 is_delete가 true인지 , is_reserved가 true인지 확인 하는 함수 필요
    // rooms 예약 날짜 최대 기간 상의해야합니다.
    @Override
    public void createReservation(Long userId,
                                  Long roomId,
                                  ReservationRequestDto.CreateReservationDto requestDto) {
        Users users = usersService.findById(userId);
        Rooms rooms = roomsService.findById(roomId);
        LocalDateTime now = LocalDateTime.now();
        if (requestDto.getCheckInTime().isBefore(now)) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION);
        }
        if (requestDto.getCheckInTime().isAfter(requestDto.getCheckOutTime())) {
            throw new ReservationCustomException(ReservationExceptionCode.BAD_REQUEST_RESERVATION);
        }
        Reservation reservation = requestDto.toEntity(users, rooms);
        reservationRepository.save(reservation);
    }

}

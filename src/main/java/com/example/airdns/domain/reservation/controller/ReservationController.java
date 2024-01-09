package com.example.airdns.domain.reservation.controller;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import com.example.airdns.domain.reservation.service.ReservationService;
import com.example.airdns.domain.reservation.service.ReservationServiceImplV1;
import com.example.airdns.global.common.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Login 구현이 끝나면 AuthenticationPrincipal로 수정
    @PostMapping("/users/{userId}/rooms/{roomsId}/reservation")
    public ResponseEntity<CommonResponse> createReservation(
            @PathVariable Long userId,
            @PathVariable Long roomsId,
            @Valid @RequestBody ReservationRequestDto.CreateReservationDto createReservation)
    {
        reservationService.createReservation(userId, roomsId, createReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse(HttpStatus.CREATED, "스터디 룸 예약 성공", null)
        );
    }

}

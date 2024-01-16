package com.example.airdns.domain.reservation.controller;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.service.ReservationService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "reservation", description = "Reservation API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {

    private final ReservationService reservationService;

    // Login 구현이 끝나면 AuthenticationPrincipal로 수정
    @PostMapping("/rooms/{roomsId}/reservation")
    @Operation(summary = "Create Reservation", description = "해당 방에 대해 예약을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 룸 예약 성공"),
            @ApiResponse(responseCode = "400", description = "예약 정보가 잘못 입력 되었습니다."),
    })
    public ResponseEntity<CommonResponse> createReservation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long roomsId,
            @Valid @RequestBody ReservationRequestDto.CreateReservationDto createReservation) {
        reservationService.createReservation(userDetails.getUser().getId(), roomsId, createReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse(HttpStatus.CREATED, "스터디 룸 예약 성공", null)
        );
    }

}

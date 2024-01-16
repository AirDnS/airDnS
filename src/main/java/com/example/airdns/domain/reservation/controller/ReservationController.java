package com.example.airdns.domain.reservation.controller;

import com.example.airdns.domain.reservation.dto.ReservationRequestDto;
import com.example.airdns.domain.reservation.dto.ReservationResponseDto;
import com.example.airdns.domain.reservation.service.ReservationService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "reservation", description = "Reservation API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/rooms/{roomsId}/reservation")
    @Operation(summary = "예약 생성", description = "해당 방에 대해 예약을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 룸 예약 성공"),
            @ApiResponse(responseCode = "400", description = "예약 시간 입력값이 잘못 입력 되었습니다."),
            @ApiResponse(responseCode = "400", description = "해당 예약 시간에 예약을 못합니다.")
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

    @PatchMapping("/rooms/{roomsId}/reservation/{reservationId}")
    @Operation(summary = "예약 수정", description = "해당 예약을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 예약 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ReservationResponseDto.UpdateReservationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "수정 정보가 잘못 입력 되었습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저가 예약한 것이 아닙니다.")
    })
    public ResponseEntity<CommonResponse> updateReservation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long roomsId,
            @PathVariable Long reservationId,
            @Valid @RequestBody ReservationRequestDto.UpdateReservationDto requestDto) {
        ReservationResponseDto.UpdateReservationResponseDto reservationResponse = reservationService.updateReservation(
                userDetails.getUser().getId(),
                roomsId,
                reservationId,
                requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse(HttpStatus.OK, "예약 수정 성공", reservationResponse)
        );
    }

    @GetMapping("/reservation/{reservationId}")
    @Operation(summary = "예약 단건 조회", description = "해당 예약을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 예약 조회에 성공",
                    content = {@Content(schema = @Schema(implementation = ReservationResponseDto.ReadReservationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "해당 예약 정보가 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저가 예약한 것이 아닙니다.")
    })
    public ResponseEntity<CommonResponse> readReservation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reservationId) {
        ReservationResponseDto.ReadReservationResponseDto reservationResponseDto = reservationService.readReservation(
                userDetails.getUser().getId(),
                reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse(HttpStatus.OK, "해당 예약 조회 성공", reservationResponseDto)
        );
    }

    @GetMapping("/reservation")
    @Operation(summary = "예약 전체 조회", description = "유저의 예약 목록을 조회한다.")
    @ApiResponse(responseCode = "200", description = "예약 목록 조회에 성공")
    public ResponseEntity<CommonResponse> readReservationList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<ReservationResponseDto.ReadReservationResponseDto> reservationResponseDtoList = reservationService.readReservationList(
                userDetails.getUser().getId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse(HttpStatus.OK, "예약 목록 조회 성공", reservationResponseDtoList)
        );
    }

}

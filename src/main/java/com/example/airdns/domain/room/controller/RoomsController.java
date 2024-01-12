package com.example.airdns.domain.room.controller;

import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
//@Tag(name = "Rooms", description = "Rooms API")
@RestController
public class RoomsController {

    private final RoomsService roomsService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create Rooms", description = "방을 등록한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 룸 등록 성공"),
            @ApiResponse(responseCode = "403", description = "등록 권한 없음"),
            @ApiResponse(responseCode = "400", description = "입력된 장비 없음")
    })
    public ResponseEntity createRooms(
            @RequestPart(value = "data") RoomsRequestDto.ReadRoomsRequestDto requestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImplV1 userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(
                HttpStatus.CREATED,
                "스터디 룸 등록에 성공했습니다",
                roomsService.createRooms(requestDto, files, userDetails.getUser())
        ));


    }


}

package com.example.airdns.domain.room.controller;

import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import lombok.AllArgsConstructor;
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
@RestController
public class RoomsController {

    private final RoomsService roomsService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createRooms(
            @RequestPart(value = "data") RoomsRequestDto.CreateRoomsRequestDto requestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImplV1 userDetails) {
        roomsService.createRooms(requestDto, files, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse(
                        HttpStatus.CREATED,
                        "스터디 룸 등록에 성공했습니다",
                        null
                )
        );
    }


}

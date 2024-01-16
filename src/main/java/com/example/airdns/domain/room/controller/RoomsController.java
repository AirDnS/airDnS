package com.example.airdns.domain.room.controller;

import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
//@Tag(name = "Rooms", description = "Rooms API")
@RestController
public class RoomsController {

    private final RoomsService roomsService;

    @PostMapping(
            value = "/rooms"
            , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create Rooms", description = "방을 등록한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 룸 등록 성공"),
            @ApiResponse(responseCode = "403", description = "등록 권한 없음"),
            @ApiResponse(responseCode = "400", description = "입력된 장비 없음")
    })
    public ResponseEntity createRooms(
            @RequestPart(value = "data") RoomsRequestDto.CreateRoomsRequestDto requestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(
                HttpStatus.CREATED,
                "스터디 룸 등록에 성공했습니다",
                roomsService.createRooms(requestDto, files, userDetails.getUser())
        ));
    }

    @GetMapping("/rooms/{roomsId}")
    @Operation(summary = "Read Rooms", description = "선택한 방 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 조회 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity readRooms(@PathVariable Long roomsId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 정보 조회에 성공했습니다",
                roomsService.readRooms(roomsId)
        ));
    }

    @GetMapping("/rooms")
    @Operation(summary = "Read Rooms List", description = "방 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 조회 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity readRoomsList(
            RoomsRequestDto.ReadRoomsListRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 정보 조회에 성공했습니다",
                roomsService.readRoomsList(requestDto)
        ));
    }

    @PatchMapping("/rooms/{roomsId}")
    @Operation(summary = "Update Rooms", description = "방을 수정한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity updateRooms(
            @PathVariable("roomsId") Long roomsId,
            @RequestBody RoomsRequestDto.UpdateRoomsRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 수정에 성공했습니다",
                roomsService.updateRooms(requestDto, roomsId, userDetails.getUser())
        ));
    }

    @PatchMapping(
            value = "/rooms/{roomsId}/updateImages",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    @Operation(summary = "Update Rooms Images", description = "방 이미지를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity updateRoomsImage(
            @PathVariable("roomsId") Long roomsId,
            @RequestPart(value = "data", required = false) RoomsRequestDto.UpdateRoomsImagesRequestDto requestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "이미지 변경에 성공했습니다",
                roomsService.updateRoomsImages(requestDto, roomsId, files, userDetails.getUser())
        ));
    }

    @DeleteMapping("/rooms/{roomsId}")
    @Operation(summary = "Delete Rooms", description = "방을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity deleteRooms(
            @PathVariable Long roomsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        roomsService.deleteRooms(roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 삭제에 성공했습니다"
        ));
    }

    @PostMapping("/rooms/{roomsId}/addRestTime")
    @Operation(summary = "Read Rooms", description = "휴식 일정을 등록한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "휴식 일정 등록 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity CreateRoomsRestSchedule(
            @PathVariable("roomsId") Long roomsId,
            @RequestBody @Valid RoomsRequestDto.CreateRoomsRestScheduleRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        roomsService.CreateRoomsRestSchedule(requestDto, roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "휴식 일정 등록에 성공했습니다"
        ));
    }

    @DeleteMapping("/rooms/{roomsId}/removeRestTime")
    @Operation(summary = "Read Rooms", description = "휴식 일정을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "휴식 일정 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity DeleteRoomsRestSchedule(
        @PathVariable("roomsId") Long roomsId,
        @RequestBody @Valid RoomsRequestDto.DeleteRoomsRestScheduleRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        roomsService.DeleteRoomsRestSchedule(requestDto, roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "휴식 일정 삭제에 성공했습니다"
        ));
    }

}

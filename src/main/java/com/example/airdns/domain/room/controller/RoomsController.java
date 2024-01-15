package com.example.airdns.domain.room.controller;

import com.example.airdns.domain.oauth2.common.OAuth2UserPrincipal;
import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @Operation(summary = "방 등록", description = "방을 등록한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 룸 등록 성공"),
            @ApiResponse(responseCode = "403", description = "등록 권한 없음"),
            @ApiResponse(responseCode = "400", description = "입력된 장비 없음")
    })
    public ResponseEntity createRooms(
            @RequestPart(value = "data") RoomsRequestDto.CreateRoomsRequestDto requestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal OAuth2UserPrincipal userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(
                HttpStatus.CREATED,
                "스터디 룸 등록에 성공했습니다",
                roomsService.createRooms(requestDto, files, userDetails.getUser())
        ));
    }

    @GetMapping("/rooms/{roomsId}")
    @Operation(summary = "선택 방 조회", description = "선택한 방 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 조회 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity readRooms(@Parameter(name = "roomsId", description = "방 ID") @PathVariable Long roomsId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 정보 조회에 성공했습니다",
                roomsService.readRooms(roomsId)
        ));
    }

    @GetMapping("/rooms")
    @Operation(summary = "방 정보 전체 조회", description = "방 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 조회 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity readRoomsList(
            @PageableDefault(size=8, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @Valid RoomsRequestDto.ReadRoomsListRequestDto requestDto) {
            RoomsRequestDto.ReadRoomsListRequestDto requestDto,
            @AuthenticationPrincipal OAuth2UserPrincipal userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 정보 조회에 성공했습니다",
                roomsService.readRoomsList(pageable, requestDto)
        ));
    }

    @PatchMapping("/rooms/{roomsId}")
    @Operation(summary = "방 정보 수정", description = "방을 수정한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity updateRooms(
            @Parameter(name = "roomsId", description = "방 ID") @PathVariable("roomsId") Long roomsId,
            @RequestBody RoomsRequestDto.UpdateRoomsRequestDto requestDto,
            @AuthenticationPrincipal OAuth2UserPrincipal userDetails) {
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
    @Operation(summary = "방 이미지 수정", description = "방 이미지를 수정한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity updateRoomsImage(
            @Parameter(name = "roomsId", description = "방 ID") @PathVariable("roomsId") Long roomsId,
            @RequestPart(value = "data", required = false) RoomsRequestDto.UpdateRoomsImagesRequestDto requestDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @AuthenticationPrincipal OAuth2UserPrincipal userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "이미지 변경에 성공했습니다",
                roomsService.updateRoomsImages(requestDto, roomsId, files, userDetails.getUser())
        ));
    }

    @DeleteMapping("/rooms/{roomsId}")
    @Operation(summary = "방 삭제", description = "방을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 룸 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity deleteRooms(
            @Parameter(name = "roomsId", description = "방 ID") 
            @PathVariable("roomsId") Long roomsId,
            @AuthenticationPrincipal OAuth2UserPrincipal userDetails) {

        roomsService.deleteRooms(roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "스터디 룸 삭제에 성공했습니다"
        ));
    }

    @PostMapping("/rooms/{roomsId}/addRestTime")
    @Operation(summary = "휴식 일정 등록", description = "휴식 일정을 등록한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "휴식 일정 등록 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity CreateRoomsRestSchedule(
            @Parameter(name = "roomsId", description = "방 ID") @PathVariable("roomsId") Long roomsId,
            @RequestBody @Valid RoomsRequestDto.CreateRoomsRestScheduleRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImplV1 userDetails) {
        roomsService.CreateRoomsRestSchedule(requestDto, roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "휴식 일정 등록에 성공했습니다"
        ));
    }

    @DeleteMapping("/rooms/{roomsId}/removeRestTime")
    @Operation(summary = "휴식 일정 삭제", description = "휴식 일정을 삭제한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "휴식 일정 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity DeleteRoomsRestSchedule(
        @Parameter(name = "roomsId", description = "방 ID") @PathVariable("roomsId") Long roomsId,
        @RequestBody @Valid RoomsRequestDto.DeleteRoomsRestScheduleRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImplV1 userDetails) {
        roomsService.DeleteRoomsRestSchedule(requestDto, roomsId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "휴식 일정 삭제에 성공했습니다"
        ));
    }

}

package com.example.airdns.domain.equipment.controller;

import com.example.airdns.domain.equipment.service.EquipmentsService;
import com.example.airdns.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Equipments", description = "Equipments API")
@RestController
public class EquipmentsController {

    private final EquipmentsService equipmentsService;

    @GetMapping("/equipments")
    @Operation(summary = "장비 전체 조회", description = "장비 정보를 조회한다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "장비 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
    })
    public ResponseEntity<CommonResponse<List<Map<String, Object>>>> readEquipmentsList() {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(
                HttpStatus.OK,
                "장비 정보 조회에 성공했습니다",
                equipmentsService.readEquipmentsList()
        ));
    }

}

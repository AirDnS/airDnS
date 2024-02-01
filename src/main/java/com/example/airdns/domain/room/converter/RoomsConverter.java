package com.example.airdns.domain.room.converter;

import com.example.airdns.domain.equipment.dto.EquipmentsResponseDto;
import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.equipmentcategory.entity.EquipmentCategories;
import com.example.airdns.domain.image.converter.ImagesConverter;
import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.restschedule.entity.RestSchedule;
import com.example.airdns.domain.room.dto.RoomsRequestDto;
import com.example.airdns.domain.room.dto.RoomsResponseDto;
import com.example.airdns.domain.room.dto.RoomsSearchConditionDto;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;

import java.util.*;
import java.util.stream.Collectors;

public class RoomsConverter {

    public static Rooms toEntity(RoomsRequestDto.CreateRoomsRequestDto requestDto, Users users) {
        return Rooms.builder()
                .users(users)
                .price(requestDto.getPrice())
                .address(requestDto.getAddress())
                .size(requestDto.getSize())
                .description(requestDto.getDesc())
                .name(requestDto.getName())
                .build();
    }

    public static RoomsResponseDto.ReadRoomsResponseDto toDto(Rooms rooms) {
        List<Map<String, Object>> equipments = getEquipmentListByRooms(rooms);

        return RoomsResponseDto.ReadRoomsResponseDto.builder()
                .roomsId(rooms.getId())
                .name(rooms.getName())
                .price(rooms.getPrice())
                .address(rooms.getAddress())
                .size(rooms.getSize())
                .isClosed(rooms.getIsClosed())
                .createdAt(rooms.getCreatedAt())
                .desc(rooms.getDescription())
                .equipment(equipments)
                .image(
                        rooms.getImagesList().stream()
                            .map((ImagesConverter::toDto))
                            .toList()
                )
                .reservatedTimeList(
                        rooms.getReservationList().stream()
                                .filter(reservation -> !reservation.getIsCancelled())
                                .map(reservation -> Arrays.asList(
                                        reservation.getCheckIn(), reservation.getCheckOut()
                                ))
                                .toList()
                )
                .restScheduleList(
                        rooms.getRestScheduleList().stream()
                                .map(restSchedule -> Arrays.asList(
                                        restSchedule.getStartTime(), restSchedule.getEndTime()
                                ))
                                .toList()
                )
                .build();
    }


    public static RoomsResponseDto.ReadRoomsRestScheduleResponseDto toDto(RestSchedule restSchedule) {
        return RoomsResponseDto.ReadRoomsRestScheduleResponseDto.builder()
                .id(restSchedule.getId())
                .startTime(restSchedule.getStartTime())
                .endTime(restSchedule.getEndTime())
                .build();
    }

    public static RoomsResponseDto.UpdateRoomsImagesResponseDto toImagesDto(Rooms rooms) {
        return RoomsResponseDto.UpdateRoomsImagesResponseDto.builder()
                .imageUrl(
                        rooms.getImagesList().stream()
                                .map((Images::getImageUrl))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static RoomsSearchConditionDto toRoomsSearchCondition(RoomsRequestDto.ReadRoomsListRequestDto requestDto) {
        return RoomsSearchConditionDto.builder()
                .cursor(requestDto.getCursor())
                .pageSize(requestDto.getPageSize())
                .keyword(requestDto.getKeyword())
                .startPrice(requestDto.getPrice() != null ? requestDto.getPrice().get(0) : null)
                .endPrice(requestDto.getPrice() != null ? requestDto.getPrice().get(1) : null)
                .startSize(requestDto.getSize() != null ? requestDto.getSize().get(0) : null)
                .endSize(requestDto.getSize() != null ? requestDto.getSize().get(1) : null)
                .equpmentList(requestDto.getEquipment())
                .build();
    }

    public static RoomsSearchConditionDto toRoomsSearchCondition(
            RoomsRequestDto.ReadRoomsListByHostRequestDto requestDto,
            Users users) {
        return RoomsSearchConditionDto.builder()
                .keyword(requestDto.getKeyword())
                .users(users)
                .build();
    }

    private static List<Map<String, Object>> getEquipmentListByRooms(Rooms rooms) {
        List<Map<String, Object>> equipmentList = new ArrayList<>();

        // map (CategoryId, equipment) 생성
        Map<EquipmentCategories, List<EquipmentsResponseDto.ReadEquipmentsResponseDto>> equipmentsMap = new HashMap<>();

        rooms.getRoomEquipmentsList()
                .forEach((roomEquipments) -> {
                    Equipments equipments = roomEquipments.getEquipments();
                    EquipmentCategories category = equipments.getEquipmentCategories();
                    List<EquipmentsResponseDto.ReadEquipmentsResponseDto> responseList =
                            equipmentsMap.getOrDefault(category, new ArrayList<>());

                    responseList.add(
                            EquipmentsResponseDto.ReadEquipmentsResponseDto.builder()
                                    .id(equipments.getId())
                                    .name(equipments.getName())
                                    .build()
                    );

                    equipmentsMap.put(category, responseList);
                });

        equipmentsMap.keySet().stream().sorted(Comparator.comparingLong(EquipmentCategories::getId))
                .forEach( category -> {
                            Map<String, Object> categoryResponseMap = new HashMap<>();
                            categoryResponseMap.put("label", category.getName());
                            categoryResponseMap.put("options", equipmentsMap.get(category));
                            equipmentList.add(categoryResponseMap);
                        }
                );

        return equipmentList;
    }
}

package com.example.airdns.domain.roomequipment.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.roomequipment.entity.RoomEquipments;
import com.example.airdns.domain.roomequipment.repository.RoomEquipmentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomEquipmentsServiceImplV1 implements RoomEquipmentsService {

    private final RoomEquipmentsRepository roomEquipmentsRepository;

    @Override
    public RoomEquipments createRoomEquipments(Rooms rooms) {
        return roomEquipmentsRepository.save(RoomEquipments.builder()
                .rooms(rooms)
                .build());
    }
}

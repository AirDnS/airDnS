package com.example.airdns.domain.roomequipment.service;

import com.example.airdns.domain.equipment.entity.Equipments;
import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.roomequipment.entity.RoomEquipments;

public interface RoomEquipmentsService {

    RoomEquipments createRoomEquipments(Rooms rooms, Equipments equipments);
}

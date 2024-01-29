package com.example.airdns.domain.deleteinfo.service;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;

public interface DeleteInfoService {
    void saveDeletedUserInfo(Users user);
    void saveDeletedRoomsInfo(Rooms room);
    void saveDeletedReservationInfo(Reservation reservation);
}

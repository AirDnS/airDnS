package com.example.airdns.domain.deleteinfo.service;

import com.example.airdns.domain.deleteinfo.entity.DeleteReservationsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteRoomsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteUsersInfo;
import com.example.airdns.domain.deleteinfo.repository.DeletePaymentsInfoRepository;
import com.example.airdns.domain.deleteinfo.repository.DeleteReservationInfoRepository;
import com.example.airdns.domain.deleteinfo.repository.DeleteRoomsInfoRepository;
import com.example.airdns.domain.deleteinfo.repository.DeleteUsersInfoRepository;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteInfoServiceImpl implements DeleteInfoService{
    private final DeleteUsersInfoRepository deleteUsersInfoRepository;
    private final DeleteRoomsInfoRepository deleteRoomsInfoRepository;
    private final DeleteReservationInfoRepository deleteReservationInfoRepository;
    private final DeletePaymentsInfoRepository deletePaymentsInfoRepository;

    @Override
    public void saveDeletedUserInfo(Users user){
        deleteUsersInfoRepository.save(
                DeleteUsersInfo.builder()
                        .deletedAt(LocalDateTime.now())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .contact(user.getContact())
                        .nickname(user.getNickname())
                        .build()
        );
    }

    @Override
    public void saveDeletedRoomsInfo(Rooms room){
        deleteRoomsInfoRepository.save(
                DeleteRoomsInfo.builder()
                        .deletedAt(LocalDateTime.now())
                        .name(room.getName())
                        .price(room.getPrice())
                        .address(room.getAddress())
                        .size(room.getSize())
                        .owner(room.getUsers().getNickname())
                        .build()
        );
    }

    @Override
    public void saveDeletedReservationInfo(Reservation reservation){
        deleteReservationInfoRepository.save(
                DeleteReservationsInfo.builder()
                        .cancelledAt(LocalDateTime.now())
                        .checkIn(reservation.getCheckIn())
                        .checkOut(reservation.getCheckOut())
                        .roomName(reservation.getRooms().getName())
                        .reserverName(reservation.getUsers().getNickname())
                        .build()
        );
    }
}

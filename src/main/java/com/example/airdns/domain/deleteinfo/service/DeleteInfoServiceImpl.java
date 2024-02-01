package com.example.airdns.domain.deleteinfo.service;

import com.example.airdns.domain.deleteinfo.entity.DeletePaymentsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteReservationsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteRoomsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteUsersInfo;
import com.example.airdns.domain.deleteinfo.repository.DeletePaymentsInfoRepository;
import com.example.airdns.domain.deleteinfo.repository.DeleteReservationInfoRepository;
import com.example.airdns.domain.deleteinfo.repository.DeleteRoomsInfoRepository;
import com.example.airdns.domain.deleteinfo.repository.DeleteUsersInfoRepository;
import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteInfoServiceImpl implements DeleteInfoService{

    private final DeleteUsersInfoRepository deleteUsersInfoRepository;
    private final DeleteRoomsInfoRepository deleteRoomsInfoRepository;
    private final DeleteReservationInfoRepository deleteReservationInfoRepository;
    private final DeletePaymentsInfoRepository deletePaymentsInfoRepository;
    private final EntityManager em;

    @Override
    @Transactional
    public void saveDeletedUserInfo(Users user){
        deleteUsersInfoRepository.save(
                DeleteUsersInfo.builder()
                        .deletedAt(LocalDateTime.now())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .contact(user.getContact())
                        .nickname(user.getNickname())
                        .role(user.getRole())
                        .build()
        );
    }

    @Override
    @Transactional
    public void saveDeletedRoomsInfo(Rooms room){

        // 1차 캐시에 있는 Rooms의 정보를 먼저 가져와서 넣기
        // Rooms saveRoom = em.find(Rooms.class, room.getId());
        Rooms saveRoom = em.createQuery(
                        "SELECT r FROM Rooms r JOIN FETCH r.users WHERE r.id = :roomId", Rooms.class)
                .setParameter("roomId", room.getId())
                .getSingleResult();

        deleteRoomsInfoRepository.save(
                DeleteRoomsInfo.builder()
                        .deletedAt(LocalDateTime.now())
                        .name(saveRoom.getName())
                        .price(saveRoom.getPrice())
                        .address(saveRoom.getAddress())
                        .size(saveRoom.getSize())
                        .description(saveRoom.getDescription())
                        .owner(saveRoom.getUsers().getNickname())
                        .build()
        );
    }

    @Override
    @Transactional
    public void saveDeletedReservationInfo(Reservation reservation){

        Reservation saveReservation = em.createQuery(
                        "SELECT res FROM Reservation res JOIN FETCH res.rooms r JOIN FETCH res.users u WHERE res.id = :reservationId", Reservation.class)
                .setParameter("reservationId", reservation.getId())
                .getSingleResult();

        deleteReservationInfoRepository.save(
                DeleteReservationsInfo.builder()
                        .cancelledAt(LocalDateTime.now())
                        .checkIn(saveReservation.getCheckIn())
                        .checkOut(saveReservation.getCheckOut())
                        .roomName(saveReservation.getRooms().getName())
                        .reserverName(saveReservation.getUsers().getNickname())
                        .build()
        );
    }

    @Override
    @Transactional
    public void saveDeletedPaymentInfo(Payment payment){
        deletePaymentsInfoRepository.save(
                DeletePaymentsInfo.builder()
                        .deletedAt(LocalDateTime.now())
                        .orderId(payment.getOrderId())
                        .amount(payment.getAmount())
                        .orderName(payment.getOrderName())
                        .orderId(payment.getOrderId())
                        .paymentKey(payment.getPaymentKey())
                        .paymentType(payment.getPaymentType())
                        .build()
        );
    }
}
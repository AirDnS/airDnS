package com.example.airdns.domain.deleteinfo.service;

import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;

public interface DeleteInfoService {

    /**
     * 삭제된 유저를 delete_users_info 테이블에 저장한다
     * @param user 유저 객체
     */
    void saveDeletedUserInfo(Users user);

    /**
     * 삭제된 유저를 delete_room_info 테이블에 저장한다
     * @param room 유저 객체
     */
    void saveDeletedRoomsInfo(Rooms room);

    /**
     * 삭제된 유저를 delete_reservation_info 테이블에 저장한다
     * @param reservation 유저 객체
     */
    void saveDeletedReservationInfo(Reservation reservation);

    /**
     * 삭제된 유저를 delete_payment_info 테이블에 저장한다
     * @param payment 유저 객체
     */
    void saveDeletedPaymentInfo(Payment payment);
}

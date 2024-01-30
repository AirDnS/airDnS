package com.example.airdns.domain.user.service;

import com.example.airdns.domain.deleteinfo.service.DeleteInfoServiceImpl;
import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.domain.reservation.service.ReservationServiceImplV1;
import com.example.airdns.domain.room.service.RoomsServiceImplV1;
import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.QUsers;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.exception.UsersExceptionCode;
import com.example.airdns.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImplV1 implements UsersService {

    private final UsersRepository usersRepository;
    private final DeleteInfoServiceImpl deleteInfoService;
    private final RoomsServiceImplV1 roomsService;
    private final ReservationServiceImplV1 reservationService;
    private final PaymentServiceImplV1 paymentService;
    @Override
    @Transactional
    public UsersResponseDto.UpdateUsersResponseDto updateUser(Long userId
            , UsersRequestDto.UpdateUserInfoRequestDto userRequestDto) {
        Users user = findById(userId);
        user.updateInfo(userRequestDto);
        return UsersResponseDto.UpdateUsersResponseDto.from(user);
    }

    @Override
    @Transactional
    public UsersResponseDto.UpdateUsersResponseDto updateUserRole(Long userId) {
        Users user = findById(userId);
        if(user.getRole().equals(UserRole.USER)){
            user.updateRole(UserRole.HOST);
        } else {
            user.updateRole(UserRole.USER);
        }

        return UsersResponseDto.UpdateUsersResponseDto.from(user);
    }

    @Override
    public UsersResponseDto.ReadUserResponseDto readUserInfo(Long userId) {
        Users user = findById(userId);
        return UsersResponseDto.ReadUserResponseDto.from(user);
    }

    @Override
    public Users findById(Long userId) {
        return usersRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(() ->
                new UsersCustomException(UsersExceptionCode.NOT_FOUND_USER));
    }

    @Override
    @Transactional
    public void deleteUsers(LocalDateTime deleteTime){
        // select id from users where isDeleted = true and deletedAt < deletedTime;
        List<Long> userIds = usersRepository.findUserIds(deleteTime);
        for (Long userId : userIds) {
            // 연관된 Rooms, Payments, Reservations의 ID 조회
            List<Long> roomIds = roomsService.findRoomIdsByUserId(userId);
            List<Long> reservationIds = reservationService.findReservationIdsByUserId(userId);
            List<Long> paymentIds = paymentService.findPaymentIdsByUserId(userId);

            // DeleteInfo 저장
            saveDeleteUserInfo(userId);
            roomIds.forEach(roomId -> roomsService.saveDeletedRoomInfo(roomId));
            reservationIds.forEach(reservationId -> reservationService.saveDeletedReservationInfo(reservationId));
            paymentIds.forEach(paymentId -> paymentService.saveDeletedPaymentInfo(paymentId));

            // 연관된 엔터티 삭제
            paymentService.deleteByUserId(userId);
            reservationIds.forEach(reservationId -> paymentService.deleteByRoomId(reservationId));
            reservationService.deleteByUserId(userId);
            roomIds.forEach(roomId -> reservationService.deleteByRoomId(roomId));
            roomsService.deleteByUserId(userId);

            // 마지막으로 Users 삭제
            usersRepository.deleteUserInfo(userId);
        }
    }
    private void saveDeleteUserInfo(Long userId){
        Users user = usersRepository.findById(userId).orElseThrow(
                ()-> new UsersCustomException(UsersExceptionCode.NOT_FOUND_USER)
        );
        deleteInfoService.saveDeletedUserInfo(user);
    }
}

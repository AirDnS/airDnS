package com.example.airdns.global.scheduler;

import com.example.airdns.domain.deleteinfo.entity.DeletePaymentsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteReservationsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteRoomsInfo;
import com.example.airdns.domain.deleteinfo.entity.DeleteUsersInfo;
import com.example.airdns.domain.deleteinfo.exception.DeleteInfoEntityCustomException;
import com.example.airdns.domain.deleteinfo.exception.DeleteInfoEntityExceptionCode;
import com.example.airdns.domain.deleteinfo.repository.*;
import com.example.airdns.domain.payment.entity.Payment;
import com.example.airdns.domain.payment.entity.QPayment;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import com.example.airdns.domain.reservation.entity.QReservation;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.exception.ReservationCustomException;
import com.example.airdns.domain.reservation.exception.ReservationExceptionCode;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.room.entity.QRooms;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.exception.RoomsCustomException;
import com.example.airdns.domain.room.exception.RoomsExceptionCode;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.user.entity.QUsers;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.exception.UsersExceptionCode;
import com.example.airdns.domain.user.repository.UsersRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final UsersRepository usersRepository;
    private final RoomsRepository roomsRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final DeleteUsersInfoRepository deleteUsersInfoRepository;
    private final DeleteRoomsInfoRepository deleteRoomsInfoRepository;
    private final DeleteReservationInfoRepository deleteReservationInfoRepository;
    private final DeletePaymentsInfoRepository deletePaymentsInfoRepository;

    private final JPAQueryFactory jpaQueryFactory;

    // 초 분 시 일 월 요일
    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    //@Scheduled(cron = "10 * * * * *")
    public void deleteEntities(){
        //LocalDateTime deleteTime = LocalDateTime.now().minusDays(1);
        LocalDateTime deleteTime = LocalDateTime.now().minusYears(1);

        // deleted_at으로부터 1년이 지난 entity의 인스턴스들을 삭제
        deleteUsers(deleteTime);
        deleteRooms(deleteTime);
        deletePayment(deleteTime);
    }

    private void deleteUsers(LocalDateTime deleteTime){
        QUsers qUsers = QUsers.users;
        QRooms qRooms = QRooms.rooms;
        QReservation qReservation = QReservation.reservation;
        QPayment qPayment = QPayment.payment;

        // select id from users where isDeleted = true and deletedAt < deletedTime;
        List<Long> userIds = jpaQueryFactory.select(qUsers.id)
                .from(qUsers)
                .where(qUsers.isDeleted.eq(true)
                        .and(qUsers.deletedAt.before(deleteTime)))
                .fetch();

        for (Long userId : userIds) {
            // 연관된 Rooms, Payments, Reservations의 ID 조회
            List<Long> roomIds = jpaQueryFactory.select(qRooms.id)
                    .from(qRooms)
                    .where(qRooms.users.id.eq(userId))
                    .fetch();
            List<Long> reservationIds = jpaQueryFactory.select(qReservation.id)
                    .from(qReservation)
                    .where(qReservation.users.id.eq(userId))
                    .fetch();
            List<Long> paymentIds = jpaQueryFactory.select(qPayment.id)
                    .from(qPayment)
                    .where(qPayment.reservation.users.id.eq(userId))
                    .fetch();

            // DeleteInfo 저장
            saveDeleteInfo("Users", userId);
            roomIds.forEach(roomId -> saveDeleteInfo("Rooms", roomId));
            reservationIds.forEach(reservationId -> saveDeleteInfo("Reservation", reservationId));
            paymentIds.forEach(paymentId -> saveDeleteInfo("Payments", paymentId));

            // 연관된 엔터티 삭제
            jpaQueryFactory.delete(qRooms)
                    .where(qRooms.users.id.eq(userId))
                    .execute();
            jpaQueryFactory.delete(qPayment)
                    .where(qPayment.reservation.users.id.eq(userId))
                    .execute();
            jpaQueryFactory.delete(qReservation)
                    .where(qReservation.users.id.eq(userId))
                    .execute();

            // 마지막으로 Users 삭제
            jpaQueryFactory.delete(qUsers)
                    .where(qUsers.id.eq(userId))
                    .execute();
        }
    }

    private void deleteRooms(LocalDateTime deleteTime){
        QRooms qRooms = QRooms.rooms;
        QReservation qReservation = QReservation.reservation;
        QPayment qPayments = QPayment.payment;

        // 삭제할 Rooms의 ID 조회
        List<Long> roomIds = jpaQueryFactory.select(qRooms.id)
                .from(qRooms)
                .where(qRooms.isDeleted.eq(true)
                        .and(qRooms.deletedAt.before(deleteTime)))
                .fetch();

        for (Long roomId : roomIds) {
            // 연관된 Reservations, Payments의 ID 조회
            List<Long> reservationIds = jpaQueryFactory.select(qReservation.id)
                    .from(qReservation)
                    .where(qReservation.rooms.id.eq(roomId))
                    .fetch();
            List<Long> paymentIds = reservationIds.stream()
                    .flatMap(reservationId -> jpaQueryFactory.select(qPayments.id)
                            .from(qPayments)
                            .where(qPayments.reservation.id.eq(reservationId))
                            .fetch().stream())
                    .collect(Collectors.toList());

            // DeleteInfo 저장
            saveDeleteInfo("Rooms", roomId);
            reservationIds.forEach(reservationId -> saveDeleteInfo("Reservation", reservationId));
            paymentIds.forEach(paymentId -> saveDeleteInfo("Payments", paymentId));

            // 연관된 엔터티 삭제
            jpaQueryFactory.delete(qPayments)
                    .where(qPayments.reservation.rooms.id.eq(roomId))
                    .execute();
            jpaQueryFactory.delete(qReservation)
                    .where(qReservation.rooms.id.eq(roomId))
                    .execute();

            // 마지막으로 Rooms 삭제
            jpaQueryFactory.delete(qRooms)
                    .where(qRooms.id.eq(roomId))
                    .execute();
        }
    }

    // #1 Feedback : checkout || cancelledAt 시간보다 1년 지나면 deleteReservationInfo에 넣기
    // checkout은 무조건 1년이 지나면 deleteTime이 흘러가야함
    // cancelledAt은 값이 존재하면 무조건 deleteTime이 흘러가야함
    // 둘 다 필드에 값이 존재하면? 우선 순위는 cancelledAt임
    // #2 Feedback : Reservation은 보다 스케줄러를 늦게 작동하게 하기(하루에 한 번 점검 할 필요가 없음으로 1주일에 한 번씩 동작하기)
    // 따로 스케줄러를 돌게 만듦
    @Transactional
    @Scheduled(cron = "0 2 1 * * SUN")
    public void deleteReservation() {
        LocalDateTime deleteTime = LocalDateTime.now().minusYears(1);
        QReservation qReservation = QReservation.reservation;
        QPayment qPayments = QPayment.payment;

        // 삭제할 Reservation의 ID 조회
        List<Long> reservationIds = jpaQueryFactory.select(qReservation.id)
                .from(qReservation)
                .where(qReservation.isCancelled.isTrue()
                        .and(qReservation.canceledAt.before(deleteTime))
                        .or(qReservation.checkOut.before(deleteTime).and(qReservation.canceledAt.isNull())))
                .fetch();

        for (Long reservationId : reservationIds) {
            // 연관된 Payments의 ID 조회
            List<Long> paymentIds = jpaQueryFactory.select(qPayments.id)
                    .from(qPayments)
                    .where(qPayments.reservation.id.eq(reservationId))
                    .fetch();

            // DeleteInfo 저장
            saveDeleteInfo("Reservation", reservationId);
            paymentIds.forEach(paymentId -> saveDeleteInfo("Payments", paymentId));

            // 연관된 Payments 삭제
            jpaQueryFactory.delete(qPayments)
                    .where(qPayments.reservation.id.eq(reservationId))
                    .execute();

            // Reservation 삭제
            jpaQueryFactory.delete(qReservation)
                    .where(qReservation.id.eq(reservationId))
                    .execute();
        }
    }

    private void deletePayment(LocalDateTime deleteTime) {
        QPayment qPayments = QPayment.payment;

        // 삭제할 Payments의 ID 조회
        List<Long> paymentIds = jpaQueryFactory.select(qPayments.id)
                .from(qPayments)
                .where(qPayments.isDeleted.eq(true)
                        .and(qPayments.deletedAt.before(deleteTime)))
                .fetch();

        for (Long paymentId : paymentIds) {
            // DeleteInfo 저장
            saveDeleteInfo("Payments", paymentId);

            // Payments 삭제
            jpaQueryFactory.delete(qPayments)
                    .where(qPayments.id.eq(paymentId))
                    .execute();
        }
    }

    private void saveDeleteInfo(String entityName, Long entityId) {
        switch (entityName) {
            case "Users" :
                Users user = usersRepository.findById(entityId).orElseThrow(
                        ()-> new UsersCustomException(UsersExceptionCode.NOT_FOUND_USER)
                );
                deleteUsersInfoRepository.save(
                        DeleteUsersInfo.builder()
                                .deletedAt(LocalDateTime.now())
                                .email(user.getEmail())
                                .address(user.getAddress())
                                .contact(user.getContact())
                                .nickname(user.getNickname())
                                .build()
                );
                break;
            case "Rooms" :
                Rooms room = roomsRepository.findById(entityId).orElseThrow(
                        ()-> new RoomsCustomException(RoomsExceptionCode.INVALID_ROOMS_ID)
                );
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
                break;
            case "Reservation" :
                Reservation reservation = reservationRepository.findById(entityId).orElseThrow(
                        ()-> new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION)
                );
                deleteReservationInfoRepository.save(
                        DeleteReservationsInfo.builder()
                                .cancelledAt(LocalDateTime.now())
                                .checkIn(reservation.getCheckIn())
                                .checkOut(reservation.getCheckOut())
                                .roomName(reservation.getRooms().getName())
                                .reserverName(reservation.getUsers().getNickname())
                                .build()
                );
                break;
            case "Payment" :
                Payment payments = paymentRepository.findById(entityId).orElseThrow(
                        ()-> new IllegalArgumentException("해당 결제 내용은 없습니다.")
                );
                deletePaymentsInfoRepository.save(
                        DeletePaymentsInfo.builder()
                                .deletedAt(LocalDateTime.now())
                                .orderId(payments.getOrderId())
                                .cancelReason(payments.getCancelReason())
                                .amount(payments.getAmount())
                                .failReason(payments.getFailReason())
                                .build()
                );
                break;
            default: throw new DeleteInfoEntityCustomException(DeleteInfoEntityExceptionCode.NOT_FOUND_ENTITY);
        }
    }
}

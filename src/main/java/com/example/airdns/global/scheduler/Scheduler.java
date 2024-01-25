package com.example.airdns.global.scheduler;

import com.example.airdns.domain.deleteinfo.entity.DeleteInfo;
import com.example.airdns.domain.deleteinfo.repository.DeleteInfoRepository;
import com.example.airdns.domain.payment.entity.Payments;
import com.example.airdns.domain.payment.entity.QPayments;
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
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final UsersRepository usersRepository;
    private final RoomsRepository roomsRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final DeleteInfoRepository deleteInfoRepository;
    private final JPAQueryFactory jpaQueryFactory;

    // 초 분 시 일 월 요일
    @Transactional
    @Scheduled(cron = "10 * * * * *")
    public void deleteEntities(){
        // 1년 지난 데이터 삭제
        // LocalDateTime deleteTime = LocalDateTime.now().minusYears(1);
        // test 1일 지난 데이터 삭제
        LocalDateTime deleteTime = LocalDateTime.now().minusDays(1);

        System.out.println("======10초마다 스케줄러 확인!=====");
        // deleted_at으로부터 1년이 지난 entity의 인스턴스들을 삭제
        deleteUsers(deleteTime);
        deleteRooms(deleteTime);
        // deleteReservation(deleteTime);
        // deletePayment(deleteTime);
    }

    private void deleteUsers(LocalDateTime deleteTime){
        QUsers qUsers = QUsers.users;
        QRooms qRooms = QRooms.rooms;
        QReservation qReservation = QReservation.reservation;
        QPayments qPayments = QPayments.payments;

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
            List<Long> paymentIds = jpaQueryFactory.select(qPayments.id)
                    .from(qPayments)
                    .where(qPayments.reservation.users.id.eq(userId))
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
            jpaQueryFactory.delete(qPayments)
                    .where(qPayments.reservation.users.id.eq(userId))
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
        QPayments qPayments = QPayments.payments;

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

    private void deleteReservations(LocalDateTime deleteTime) {
        QReservation qReservation = QReservation.reservation;
        QPayments qPayments = QPayments.payments;

        // 삭제할 Reservation의 ID 조회
        List<Long> reservationIds = jpaQueryFactory.select(qReservation.id)
                .from(qReservation)
                .where(qReservation.isDeleted.eq(true)
                        .and(qReservation.deletedAt.before(deleteTime)))
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

    private void deletePayments(LocalDateTime deleteTime) {
        QPayments qPayments = QPayments.payments;

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
                deleteInfoRepository.save(
                        DeleteInfo.builder()
                                .entityName(entityName)
                                .deletedAt(LocalDateTime.now())
                                .email(user.getEmail())
                                .address(user.getAddress())
                                .contact(user.getContact())
                                .build()
                );
                break;
            case "Rooms" :
                Rooms room = roomsRepository.findById(entityId).orElseThrow(
                        ()-> new RoomsCustomException(RoomsExceptionCode.INVALID_ROOMS_ID)
                );
                deleteInfoRepository.save(
                        DeleteInfo.builder()
                                .entityName(entityName)
                                .deletedAt(LocalDateTime.now())
                                .roomName(room.getName())
                                .roomPrice(room.getPrice())
                                .address(room.getAddress())
                                .roomSize(room.getSize())
                                .build()
                );
                break;
            case "Reservation" :
                Reservation reservation = reservationRepository.findById(entityId).orElseThrow(
                        ()-> new ReservationCustomException(ReservationExceptionCode.NOT_FOUND_RESERVATION)
                );
                deleteInfoRepository.save(
                        DeleteInfo.builder()
                                .entityName(entityName)
                                .deletedAt(LocalDateTime.now())
                                .checkIn(reservation.getCheckIn())
                                .checkOut(reservation.getCheckOut())
                                .build()
                );
                break;
            case "Payment" :
                Payments payments = paymentRepository.findById(entityId).orElseThrow(
                        ()-> new IllegalArgumentException("해당 결제 내용은 없습니다.")
                );
                deleteInfoRepository.save(
                        DeleteInfo.builder()
                                .entityName(entityName)
                                .deletedAt(LocalDateTime.now())
                                .failReason(payments.getFailReason())
                                .build()
                );
                break;
            default: throw new IllegalArgumentException("잘못된 엔티티 입력입니다.");
        }
    }
}

package com.example.airdns.global.scheduler;

import com.example.airdns.domain.deleteinfo.entity.DeleteInfo;
import com.example.airdns.domain.deleteinfo.repository.DeleteInfoRepository;
import com.example.airdns.domain.payment.entity.Payments;
import com.example.airdns.domain.payment.repository.PaymentRepository;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.reservation.repository.ReservationRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    @PersistenceContext
    private EntityManager entityManager;

    private final UsersRepository usersRepository;
    private final DeleteInfoRepository deleteInfoRepository;
    private final RoomsRepository roomsRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    // 초 분 시 일 월 요일
    @Transactional
    @Scheduled(cron = "10 * * * * *")
    public void deleteEntities(){
        // 1년 지난 데이터 삭제
        // LocalDateTime deleteTime = LocalDateTime.now().minusYears(1);
        // test 1일 지난 데이터 삭제
        LocalDateTime deleteTime = LocalDateTime.now().minusDays(1);

        // System.out.println("======10초마다 스케줄러 확인!=====");
        // deleted_at으로부터 1년이 지난 entity의 인스턴스들을 삭제
        deleteUsers(deleteTime);
        // deleteRooms(deleteTime);
        // deleteReservation(deleteTime);
        // deletePayment(deleteTime);
    }

    private void deleteUsers(LocalDateTime deleteTime){
        List<Users> deleteUserList = usersRepository.findByIsDeletedTrueAndDeletedAtBefore(deleteTime);
        for (Users user : deleteUserList) {
            // 연관된 Reservation 인스턴스 삭제
            // List<Reservation> reservations = reservationRepository.findByUsers(user);
            // for (Reservation reservation : reservations) {
            //     entityManager.createQuery("DELETE FROM Reservation rv WHERE rv.id = :id")
            //             .setParameter("id", reservation.getId())
            //             .executeUpdate();
            // }

            DeleteInfo deleteInfo = DeleteInfo.builder()
                    .deletedAt(LocalDateTime.now())
                    .entityName("Users")
                    .build();
            deleteInfoRepository.save(deleteInfo);

            entityManager.createQuery("DELETE FROM Users u WHERE u.id = :id")
                    .setParameter("id", user.getId())
                    .executeUpdate();
        }
    }


    /*private void deleteRooms(LocalDateTime deleteTime){
        List<Rooms> deleteRoomList = roomsRepository.findByIsDeletedTrueAndDeletedAtBefore(deleteTime);
        for (Rooms room : deleteRoomList) {
            DeleteInfo deleteInfo = DeleteInfo.builder()
                    .deletedAt(LocalDateTime.now())
                    .entityName("Rooms")
                    .build();
            deleteInfoRepository.save(deleteInfo);

            entityManager.createQuery("DELETE FROM Rooms r WHERE r.id = :id")
                    .setParameter("id", room.getId())
                    .executeUpdate();
        }
    }

    private void deleteReservation(LocalDateTime deleteTime){
        List<Reservation> deleteReservationList = reservationRepository.findByIsDeletedTrueAndDeletedAtBefore(deleteTime);
        for (Reservation reservation : deleteReservationList) {
            DeleteInfo deleteInfo = DeleteInfo.builder()
                    .deletedAt(LocalDateTime.now())
                    .entityName("Reservation")
                    .build();
            deleteInfoRepository.save(deleteInfo);

            entityManager.createQuery("DELETE FROM Reservation rv WHERE rv.id = :id")
                    .setParameter("id", reservation.getId())
                    .executeUpdate();
        }
    }

    private void deletePayment(LocalDateTime deleteTime){
        List<Payments> deletePaymentList = paymentRepository.findByIsDeletedTrueAndDeletedAtBefore(deleteTime);
        for (Payments payment : deletePaymentList) {
            DeleteInfo deleteInfo = DeleteInfo.builder()
                    .deletedAt(LocalDateTime.now())
                    .entityName("Payment")
                    .build();
            deleteInfoRepository.save(deleteInfo);

            entityManager.createQuery("DELETE FROM Reservation rv WHERE rv.id = :id")
                    .setParameter("id", payment.getId())
                    .executeUpdate();
        }
    }*/
}

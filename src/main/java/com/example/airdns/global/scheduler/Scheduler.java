package com.example.airdns.global.scheduler;

import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.domain.reservation.servicefacade.ReservationServiceFacadeImplV1;
import com.example.airdns.domain.room.servicefacade.RoomsServiceFacadeImplV1;
import com.example.airdns.domain.user.service.UsersServiceImplV1;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {

    private final UsersServiceImplV1 usersService;
    private final RoomsServiceFacadeImplV1 roomsServiceFacade;
    private final ReservationServiceFacadeImplV1 reservationServiceFacade;
    private final PaymentServiceImplV1 paymentService;

    // 초 분 시 일 월 요일
    //@Transactional
    @Scheduled(cron = "0 0 1 * * *")
    //@Scheduled(cron = "10 * * * * *")
    public void deleteEntities(){
        //LocalDateTime deleteTime = LocalDateTime.now().minusDays(1);
        LocalDateTime deleteTime = LocalDateTime.now().minusYears(1);

        // deleted_at으로부터 1년이 지난 entity의 인스턴스들을 삭제
        usersService.deleteUsers(deleteTime);
        roomsServiceFacade.deleteRooms(deleteTime);
        reservationServiceFacade.deleteReservation(deleteTime);
        paymentService.deletePayment(deleteTime);

    }
}

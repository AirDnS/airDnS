package com.example.airdns.domain.payment.entity;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payments extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String paymentType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false, name = "pay_name")
    private String orderName;

    @Column(nullable = false, name = "order_id")
    private Long orderId;

    @Column
    private Boolean isCanceled;

    @Column
    private Boolean isPaySuccess;

    @Column
    private String paymentKey;

    @Column
    private String failReason;

    @Column
    private String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}

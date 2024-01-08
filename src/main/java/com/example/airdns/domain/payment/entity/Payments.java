package com.example.airdns.domain.payment.entity;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payments extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String paymentType;

    @Column
    private Long amount;

    @Column
    private Boolean isCanceled;

    @Column
    private Boolean isPaySuccess;

    @Column
    private String failReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}

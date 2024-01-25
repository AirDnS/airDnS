package com.example.airdns.domain.payment.entity;

import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE payments SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP whwere id = ?")
public class Payments extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String paymentType;

    @Column(nullable = false)
    private Long amount;

    @Column
    private String orderName;

    @Column(nullable = false, name = "order_id")
    private String orderId;

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

    @Column
    private Boolean isDeleted = Boolean.FALSE;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}

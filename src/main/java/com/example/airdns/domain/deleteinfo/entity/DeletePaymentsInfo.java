package com.example.airdns.domain.deleteinfo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deletePaymentInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeletePaymentsInfo {

    // 공통 DeleteInfo column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime deletedAt;

    // Payment DeleteInfo column
    private String failReason;

    @Column
    private String paymentType;

    @Column(nullable = false)
    private Long amount;

    @Column
    private String orderName;

    @Column(nullable = false, name = "order_id")
    private String orderId;

    @Column
    private String paymentKey;

    @Column
    private String cancelReason;
}

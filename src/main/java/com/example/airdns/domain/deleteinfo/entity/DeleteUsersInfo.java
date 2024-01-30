package com.example.airdns.domain.deleteinfo.entity;

import com.example.airdns.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deleteUsersInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteUsersInfo {

    // 공통 DeleteInfo column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime deletedAt;

    @Column
    // User DeleteInfo column
    private String address;

    @Column
    private String contact;

    @Column
    private String email;

    @Column
    private String nickname;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

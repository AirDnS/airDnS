package com.example.airdns.domain.chattinguser.entity;

import com.example.airdns.domain.chatting.entity.Chatting;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatting_users")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingUsers extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_id")
    private Chatting chatting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;
}

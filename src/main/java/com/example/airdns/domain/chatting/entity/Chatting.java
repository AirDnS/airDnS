package com.example.airdns.domain.chatting.entity;


import com.example.airdns.domain.chattinguser.entity.ChattingUsers;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatting")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chatting extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "chatting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChattingUsers> chattingUsersList = new ArrayList<>();

}

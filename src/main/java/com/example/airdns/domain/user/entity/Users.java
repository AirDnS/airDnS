package com.example.airdns.domain.user.entity;

import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.oauth2.common.OAuth2Provider;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Users extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column
    private String nickname;

    @Column
    private String contact;

    @Column
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder.Default
    @Column
    private Boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Reviews> reviewsList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Rooms> roomsList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Likes> likesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
    private List<Reservation> reservationList = new ArrayList<>();

    public Users update(String email, OAuth2Provider provider) {
        this.email = email;
        this.provider = provider;
        return this;
    }

    public void updateInfo(UsersRequestDto.UpdateUserInfoRequestDto userRequestDto) {
        this.address = userRequestDto.getAddress();
        this.contact = userRequestDto.getContact();
        this.nickname = userRequestDto.getNickname();
    }

    public void updateRole(UserRole role) {
        this.role = role;
    }
}

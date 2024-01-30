package com.example.airdns.domain.user.entity;

import com.example.airdns.domain.oauth2.common.OAuth2Provider;
import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

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

    @Builder.Default
    @Column
    private String name = "Default";

    @Builder.Default
    @Column
    private String contact = "Default";

    @Builder.Default
    @Column
    private String address = "Default";

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder.Default
    @Column
    private Boolean isDeleted = Boolean.FALSE;

    @Column
    private LocalDateTime deletedAt;

    public Users update(String email, OAuth2Provider provider) {
        this.email = email;
        this.provider = provider;
        return this;
    }

    public void updateInfo(UsersRequestDto.UpdateUserInfoRequestDto userRequestDto) {
        if (!userRequestDto.getName().isBlank()) {
            this.name = userRequestDto.getName();
        }
        if (!userRequestDto.getAddress().isBlank()) {
            this.address = userRequestDto.getAddress();
        }
        if (!userRequestDto.getContact().isBlank()) {
            this.contact = userRequestDto.getContact();
        }
        if (!userRequestDto.getNickname().isBlank()) {
            this.nickname = userRequestDto.getNickname();
        }
    }

    public void updateRole(UserRole role) {
        this.role = role;
    }

}

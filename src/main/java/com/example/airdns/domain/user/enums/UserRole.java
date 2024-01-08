package com.example.airdns.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    USER(Authority.USER),
    ADMIN(Authority.ADMIN),
    HOST(Authority.HOST);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {

        public static final String USER = "ROLE_USER";

        public static final String ADMIN = "ROLE_ADMIN";

        public static final String HOST = "ROLE_HOST";
    }
}

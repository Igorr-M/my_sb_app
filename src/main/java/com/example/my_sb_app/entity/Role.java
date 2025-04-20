package com.example.my_sb_app.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    public String getAuthority() {
        return name();
    }

}

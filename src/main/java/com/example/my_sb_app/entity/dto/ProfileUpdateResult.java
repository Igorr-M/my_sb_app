package com.example.my_sb_app.entity.dto;

public class ProfileUpdateResult {
    private boolean emailChanged;
    private boolean passwordChanged;

    public ProfileUpdateResult(boolean emailChanged, boolean passwordChanged) {
        this.emailChanged = emailChanged;
        this.passwordChanged = passwordChanged;
    }

    public boolean isEmailChanged() {
        return emailChanged;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }
}

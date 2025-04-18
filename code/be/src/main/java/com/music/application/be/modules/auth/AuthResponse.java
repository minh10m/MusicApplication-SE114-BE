package com.music.application.be.modules.auth;

// Lớp DTO cho response
public class AuthResponse {
    private boolean success;
    private String message;
    private String token;

    // Constructor, getters và setters
    public AuthResponse(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    // Các getter và setter
}

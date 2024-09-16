package com.fotografia.fotografia.controllers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AuthResponse {

    private Integer userId;
    private String token;

    public AuthResponse(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
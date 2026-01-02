package com.example.auth;

public class LoginUser {
    private final String username;
    private final String role; // "USER" or "ADMIN"

    public LoginUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
}

package com.wecode.bookit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
public class LoginRequest {
    //private String username;

    // FIX 2: Add this so Spring can save the incoming email!
    private String email;

    @Getter
    private String password;

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // FIX 1: Correct casing
    public String getemail() {
        return email;
    }

}
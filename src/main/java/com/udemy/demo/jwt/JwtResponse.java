package com.udemy.demo.jwt;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    @Setter
    @Getter
    private String userName;

    public JwtResponse(String username) {
        this.userName = username;
    }
}
package com.udemy.demo.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JwtResponse {
    @Setter
    @Getter
    private String userName;

    public JwtResponse(String username) {
        this.userName = username;
    }
}
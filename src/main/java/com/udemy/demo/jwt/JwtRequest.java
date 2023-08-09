package com.udemy.demo.jwt;

import lombok.Getter;
import lombok.Setter;

public class JwtRequest {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

}
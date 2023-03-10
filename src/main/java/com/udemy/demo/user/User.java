package com.udemy.demo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    @Size(min = 2, max = 25, message = "Le prenom doit faire entre 2 et 25 caractères")
    private String fistName;
    @Size(min = 2, max = 25, message = "Le nom doit faire entre 2 et 25 caractères")
    private String lastName;
    private String password;

    public User(String email) {
        this.email = email;
    }
}

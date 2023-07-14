package com.udemy.demo.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    @Size(min = 2, max = 25, message = "Le prenom doit faire entre 2 et 25 caractères")
    private String fistName;
    @Size(min = 2, max = 25, message = "Le nom doit faire entre 2 et 25 caractères")
    private String lastName;
    private String password;

    public UserInfo(String email) {
        this.email = email;
    }
}

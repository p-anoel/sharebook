package com.udemy.demo.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Size;

@RestController
public class UserController {

    @PostMapping(value = "/users")
    public ResponseEntity addUser (@Valid @RequestBody User user){
        User userOld = new User("pierre@gmail.com");
        return new ResponseEntity(userOld, HttpStatus.CREATED);
    }

}

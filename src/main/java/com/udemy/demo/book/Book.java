package com.udemy.demo.book;

import com.udemy.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    private BookStatus status;

    private boolean deleted;

    @Transient
    private int cartegoryId;
}

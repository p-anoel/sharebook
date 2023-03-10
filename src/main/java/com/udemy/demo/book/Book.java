package com.udemy.demo.book;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Book {
    @NotBlank
    private String title;
    private Category category;
    private BookStatus bookStatus;
}

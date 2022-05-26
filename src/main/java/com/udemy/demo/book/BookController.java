package com.udemy.demo.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class BookController {

    @GetMapping(value = "/books")
    public ResponseEntity listBooks(){
        Book book = new Book();
        book.setTitle("Tintin");
        book.setCategory(new Category("BD"));

        return new ResponseEntity(Arrays.asList(book), HttpStatus.OK);
    }
}

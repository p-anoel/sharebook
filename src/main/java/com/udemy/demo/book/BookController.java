package com.udemy.demo.book;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Size;
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

    @PostMapping(value = "/books")
    public ResponseEntity addBook(@Valid @RequestBody Book book){
        return new ResponseEntity(Arrays.asList(book), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/books/{bookId}")
    public ResponseEntity deleteBook(@PathVariable("bookId") String bookId){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId") String bookId, @RequestBody Book book){
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/categories")
    public ResponseEntity listCategories(){
        Category category = new Category("BD");
        return new ResponseEntity(Arrays.asList(category), HttpStatus.OK);
    }
}

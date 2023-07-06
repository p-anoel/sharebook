package com.udemy.demo.book;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @GetMapping(value = "/books")
    public ResponseEntity listBooks(@RequestParam(required = false) BookStatus status){
        Integer userConnectedId = this.getUserConnectedId();
        List<Book> books;
        if (status == BookStatus.FREE){
            books = bookRepository.findByStatusAndUserIdNotAndDeletedFalse(status, userConnectedId);
        }else {
            books = bookRepository.findByUserIdAndDeletedFalse(userConnectedId);
        }
        return new ResponseEntity(books, HttpStatus.OK);
    }

    private Integer getUserConnectedId() {
        return 1;
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
        Category category = new Category(1, "BD");
        return new ResponseEntity(Arrays.asList(category), HttpStatus.OK);
    }
}

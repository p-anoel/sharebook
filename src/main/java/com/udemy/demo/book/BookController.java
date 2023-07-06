package com.udemy.demo.book;

import com.udemy.demo.user.User;
import com.udemy.demo.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

        Integer userConnectedId = this.getUserConnectedId();
        Optional<User> user = userRepository.findById(userConnectedId);
        Optional<Category> category = categoryRepository.findById(book.getCartegoryId());

        if (category.isPresent()){
            book.setCategory(category.get());
        } else {
            return new ResponseEntity("You must provide a valid category", HttpStatus.BAD_REQUEST);
        }

        if (user.isPresent()){
            book.setUser(user.get());
        } else {
            return new ResponseEntity("You must provide a valid user", HttpStatus.BAD_REQUEST);
        }

        book.setDeleted(false);
        book.setStatus(BookStatus.FREE);
        bookRepository.save(book);

        return new ResponseEntity(book, HttpStatus.CREATED);
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

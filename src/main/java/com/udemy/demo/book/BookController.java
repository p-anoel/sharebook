package com.udemy.demo.book;

import com.udemy.demo.borrow.Borrow;
import com.udemy.demo.borrow.BorrowRepository;
import com.udemy.demo.user.UserInfo;
import com.udemy.demo.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value = "/books")
    public ResponseEntity listBooks(@RequestParam(required = false) BookStatus status, Principal principal){
        Integer userConnectedId = getUserConnectedId(principal);
        List<Book> books;
        if (status == BookStatus.FREE){
            books = bookRepository.findByStatusAndUserIdNotAndDeletedFalse(status, userConnectedId);
        }else {
            books = bookRepository.findByUserIdAndDeletedFalse(userConnectedId);
        }
        return new ResponseEntity(books, HttpStatus.OK);
    }

    public Integer getUserConnectedId(Principal principal) {
        if (!(principal instanceof UsernamePasswordAuthenticationToken)){
            throw new RuntimeException("User not found");
        }
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
        UserInfo oneByEmail = userRepository.findOneByEmail(user.getName());

        return oneByEmail.getId();
    }

    @PostMapping(value = "/books")
    public ResponseEntity addBook(@Valid @RequestBody Book book, Principal principal){

        Integer userConnectedId = getUserConnectedId(principal);
        Optional<UserInfo> user = userRepository.findById(userConnectedId);
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
        Optional<Book> bookToDelete = bookRepository.findById(Integer.valueOf(bookId));
        if(bookToDelete.isEmpty()){
            return new ResponseEntity("Book not found", HttpStatus.BAD_REQUEST);
        }
        Book book = bookToDelete.get();
        List<Borrow> borrows = borrowRepository.findByBookId(book.getId());
        for (Borrow borrow : borrows){
            if (borrow.getCloseDate() == null){
                UserInfo borrower = borrow.getBorrower();
                return new ResponseEntity(borrower, HttpStatus.CONFLICT);
            }
        }
        book.setDeleted(true);
        bookRepository.save(book);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/books/{bookId}")
    public ResponseEntity updateBook(@PathVariable("bookId") String bookId, @RequestBody Book book){

        Optional<Book> bookToUpdate = bookRepository.findById(Integer.valueOf(bookId));

        if (bookToUpdate.isEmpty()){
            return new ResponseEntity("Book not existing", HttpStatus.BAD_REQUEST);
        }

        Book bookToSave = bookToUpdate.get();

        Optional<Category> newCategory = categoryRepository.findById(bookToSave.getCartegoryId());
        bookToSave.setCategory(newCategory.get());
        bookToSave.setTitle(book.getTitle());

        return new ResponseEntity(bookToSave,  HttpStatus.OK);
    }

    @GetMapping(value = "/categories")
    public ResponseEntity listCategories(){
        return new ResponseEntity(categoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/books/{bookId}")
    public ResponseEntity loadBook(@PathVariable("bookId") String bookId){

        Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));

        return book.map(value -> new ResponseEntity(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity("Book not found", HttpStatus.BAD_REQUEST));

    }
}

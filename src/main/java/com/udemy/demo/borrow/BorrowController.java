package com.udemy.demo.borrow;

import com.udemy.demo.book.Book;
import com.udemy.demo.book.BookController;
import com.udemy.demo.book.BookRepository;
import com.udemy.demo.book.BookStatus;
import com.udemy.demo.user.User;
import com.udemy.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class BorrowController {

    @Autowired
    BorrowRepository borrowRepository;
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/borrows")
    public ResponseEntity listBorrow(){
        List<Borrow> borrows = borrowRepository.findByBorrowerId(BookController.getUserConnectedId());
        return new ResponseEntity(borrows, HttpStatus.OK);
    }

    @PostMapping("/borrows/{bookId}")
    public ResponseEntity createBorrow(@PathVariable("bookId") String bookId){
        
        Integer userConnectedId = BookController.getUserConnectedId();
        Optional<User> borrower = userRepository.findById(userConnectedId);
        Optional<Book> book = bookRepository.findById(Integer.valueOf(bookId));

        if (borrower.isPresent() && book.isPresent() && book.get().getStatus().equals(BookStatus.FREE)){
            Borrow borrow = new Borrow();
            Book bookEntity = book.get();
            borrow.setBook(bookEntity);
            borrow.setBorrower(borrower.get());
            borrow.setLender(book.get().getUser());
            borrow.setAskDate(LocalDate.now());
            borrowRepository.save(borrow);

            bookEntity.setStatus(BookStatus.BORROWED);
            bookRepository.save(bookEntity);

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/borrows/{borrowId}")
    public ResponseEntity deleteBorrow(@PathVariable("borrowId") String borrowId){

        Optional<Borrow> borrow = borrowRepository.findById(Integer.valueOf(borrowId));

        if (borrow.isPresent()){
            Book book = borrow.get().getBook();
            book.setStatus(BookStatus.FREE);
            bookRepository.save(book);

            borrow.get().setCloseDate(LocalDate.now());
            borrowRepository.save(borrow.get());

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}

package com.udemy.demo.borrow;

import com.udemy.demo.book.Book;
import com.udemy.demo.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Borrow {
    private User borrower;
    private User lender;
    private Book book;
    private LocalDate askDate;
    private LocalDate closeDate;
}

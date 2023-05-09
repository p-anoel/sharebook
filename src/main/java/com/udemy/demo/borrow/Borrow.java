package com.udemy.demo.borrow;

import com.udemy.demo.book.Book;
import com.udemy.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User borrower;
    @ManyToOne
    private User lender;
    @ManyToOne
    private Book book;
    private LocalDate askDate;
    private LocalDate closeDate;
}

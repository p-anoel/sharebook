package com.udemy.demo.borrow;

import com.udemy.demo.book.Book;
import com.udemy.demo.user.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserInfo borrower;
    @ManyToOne
    private UserInfo lender;
    @ManyToOne
    private Book book;
    private LocalDate askDate;
    private LocalDate closeDate;
}

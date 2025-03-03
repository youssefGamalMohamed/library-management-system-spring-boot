package com.youssef.gamal.library_magement_system_app.borrowing;

import java.io.Serializable;
import java.time.LocalDate;

import com.youssef.gamal.library_magement_system_app.book.Book;
import com.youssef.gamal.library_magement_system_app.patron.Patron;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Borrowing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate borrowingDate;

    private LocalDate dateMustReturnIn;

    private LocalDate actualReturnDate;

    private Integer paidFeesAmount;
}

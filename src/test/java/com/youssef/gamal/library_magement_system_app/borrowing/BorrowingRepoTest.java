package com.youssef.gamal.library_magement_system_app.borrowing;

import com.youssef.gamal.library_magement_system_app.book.Book;
import com.youssef.gamal.library_magement_system_app.book.BookRepo;
import com.youssef.gamal.library_magement_system_app.patron.Patron;
import com.youssef.gamal.library_magement_system_app.patron.PatronRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BorrowingRepoTest {

    @Autowired
    private BorrowingRepo borrowingRepo;

    @Autowired
    private PatronRepo patronRepo;

    @Autowired
    private BookRepo bookRepo;



    @Test
    public void BorrowingRepo_SaveBorrowing_ReturnSavedBorrowing() {

        //Arrange
        Book book = Book.builder()
                .title("Book Title")
                .isbn("Book ISBN")
                .description("Book Description")
                .publicationYear(Year.now())
                .author("Book Author")
                .build();
        Patron patron = Patron.builder()
                .name("Patron Name")
                .phone("Patron Phone")
                .address("Patron Address")
                .email("Patron Email")
                .build();
        book = bookRepo.save(book);
        patron = patronRepo.save(patron);


        //Act
        Borrowing borrowing = Borrowing.builder()
                .id(1L)
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .dateMustReturnIn(LocalDate.now().plusDays(1))
                .actualReturnDate(null)
                .build();
        Borrowing savedBorrowing = borrowingRepo.save(borrowing);

        //Assert
        Assertions.assertThat(savedBorrowing).isNotNull();
        Assertions.assertThat(savedBorrowing.getBook().getId()).isNotNull();
        Assertions.assertThat(savedBorrowing.getBook().getId()).isEqualTo(book.getId());
        Assertions.assertThat(savedBorrowing.getPatron().getId()).isNotNull();
        Assertions.assertThat(savedBorrowing.getPatron().getId()).isEqualTo(patron.getId());
    }



    @Test
    public void BorrowingRepo_FindBorrowingByBookIdAndPatronId_ReturnBorrowing() {

        //Arrange
        Book book = Book.builder()
                .title("Book Title")
                .isbn("Book ISBN")
                .description("Book Description")
                .publicationYear(Year.now())
                .author("Book Author")
                .build();
        Patron patron = Patron.builder()
                .name("Patron Name")
                .phone("Patron Phone")
                .address("Patron Address")
                .email("Patron Email")
                .build();
        book = bookRepo.save(book);
        patron = patronRepo.save(patron);
        Borrowing borrowing = Borrowing.builder()
                .id(1L)
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .dateMustReturnIn(LocalDate.now().plusDays(1))
                .actualReturnDate(null)
                .build();
        Borrowing savedBorrowing = borrowingRepo.save(borrowing);

        //Act
        Borrowing borrowingFound = borrowingRepo.findBorrowingByPatronAndBook(patron,book).get();

        //Assert
        Assertions.assertThat(borrowingFound).isNotNull();
        Assertions.assertThat(savedBorrowing.getId()).isEqualTo(borrowingFound.getId());
    }


}

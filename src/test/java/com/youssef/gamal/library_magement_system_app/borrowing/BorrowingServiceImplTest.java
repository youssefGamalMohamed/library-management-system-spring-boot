package com.youssef.gamal.library_magement_system_app.borrowing;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.youssef.gamal.library_magement_system_app.book.Book;
import com.youssef.gamal.library_magement_system_app.book.BookServiceImpl;
import com.youssef.gamal.library_magement_system_app.patron.Patron;
import com.youssef.gamal.library_magement_system_app.patron.PatronServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceImplTest {

    private static final int BOOK_DELIVERY_FEES_PER_DAY = 10;

    @Mock
    private BorrowingRepo borrowingRepo;

    @Mock
    private PatronServiceImpl patronServiceImpl;

    @Mock
    private BookServiceImpl bookServiceImpl;

    @InjectMocks
    private BorrowingServiceImpl borrowingServiceImpl;




    @Test
    void BorrowingService_BorrowBook_Success_ReturnVoid() {
        //Arrange
        Book book = Book.builder()
                .id(1L)
                .title("book")
                .isbn("123")
                .author("author")
                .publicationYear(Year.of(2020))
                .description("desc")
                .build();

        Patron patron = Patron.builder()
                .id(1L)
                .address("address")
                .email("email")
                .name("name")
                .phone("phone")
                .build();

        when(bookServiceImpl.findById(book.getId()))
                .thenReturn(book);
        when(patronServiceImpl.findById(patron.getId()))
                .thenReturn(patron);

        when(borrowingRepo.save(any(Borrowing.class)))
                .thenReturn(any(Borrowing.class));

        //Assert
        assertAll(
                () -> borrowingServiceImpl.borrowBook(book.getId(), patron.getId(), LocalDate.now().plusDays(10))
        );
    }

    @Test
    void BorrowingService_ReturnBook_Success_ReturnVoid() {
        //Arrange
        Book book = Book.builder()
                .id(1L)
                .title("book")
                .isbn("123")
                .author("author")
                .publicationYear(Year.of(2020))
                .description("desc")
                .build();

        Patron patron = Patron.builder()
                .id(1L)
                .address("address")
                .email("email")
                .name("name")
                .phone("phone")
                .build();

        when(bookServiceImpl.findById(book.getId()))
                .thenReturn(book);
        when(patronServiceImpl.findById(patron.getId()))
                .thenReturn(patron);

        when(borrowingRepo.save(any(Borrowing.class)))
                .thenReturn(any(Borrowing.class));

        //Assert
        assertAll(
                () -> borrowingServiceImpl.borrowBook(book.getId(), patron.getId(), LocalDate.now().plusDays(10))
        );
    }

    @Test
    public void BorrowingService_returnBook_shouldReturnDelayFeesMustBePaid_whenActualReturnDateIsAfterDateMustReturnIn() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        LocalDate actualReturnDate = LocalDate.now().plusDays(5);
        LocalDate dateMustReturnIn = LocalDate.now().plusDays(3);


        Book book = Book.builder()
                .id(bookId)
                .build();
        Patron patron =  Patron.builder()
                .id(patronId)
                .build();
        Borrowing borrowing = Borrowing.builder()
                .id(1L)
                .book(book)
                .patron(patron)
                .dateMustReturnIn(dateMustReturnIn)
                .build();
        when(bookServiceImpl.findById(bookId)).thenReturn(book);
        when(patronServiceImpl.findById(patronId)).thenReturn(patron);
        when(borrowingRepo.findBorrowingByPatronAndBook(patron,book)).thenReturn(java.util.Optional.of(borrowing));
        when(borrowingRepo.save(any(Borrowing.class))).thenReturn(borrowing);

        // Act
        Borrowing returnBorrowingResult =  borrowingServiceImpl.returnBook(bookId, patronId, actualReturnDate);
        int delayFeesMustBePaid = returnBorrowingResult.getPaidFeesAmount();

        // Assert
        assertEquals(2 * BOOK_DELIVERY_FEES_PER_DAY, delayFeesMustBePaid);
    }

    @Test
    public void BorrowingService_ReturnBook_shouldReturnZero_whenActualReturnDateIsBeforeOrEqualToDateMustReturnIn() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        LocalDate actualReturnDate = LocalDate.now().minusDays(5);
        LocalDate dateMustReturnIn = LocalDate.now().plusDays(3);

        Book book = Book.builder()
                .id(bookId)
                .build();
        Patron patron =  Patron.builder()
                .id(patronId)
                .build();
        Borrowing borrowing = Borrowing.builder()
                .book(book)
                .patron(patron)
                .dateMustReturnIn(dateMustReturnIn)
                .build();

        when(bookServiceImpl.findById(bookId)).thenReturn(book);
        when(patronServiceImpl.findById(patronId)).thenReturn(patron);
        when(borrowingRepo.save(any(Borrowing.class))).thenReturn(borrowing);
        when(borrowingRepo.findBorrowingByPatronAndBook(patron,book)).thenReturn(java.util.Optional.of(borrowing));

        // Act
        int delayFeesMustBePaid = borrowingServiceImpl.returnBook(bookId, patronId, actualReturnDate).getPaidFeesAmount();

        // Assert
        assertEquals(0, delayFeesMustBePaid);
    }

}

package com.youssef.gamal.library_magement_system_app.borrowing;

import com.youssef.gamal.library_magement_system_app.book.Book;
import com.youssef.gamal.library_magement_system_app.book.BookServiceInterface;
import com.youssef.gamal.library_magement_system_app.patron.Patron;
import com.youssef.gamal.library_magement_system_app.patron.PatronServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;


@Service
@AllArgsConstructor
@Transactional
public class BorrowingServiceImpl implements BorrowingServiceInterface {

    private final BorrowingRepo borrowingRepo;

    private final PatronServiceInterface patronServiceInterface;

    private final BookServiceInterface bookServiceInterface;

    private static final int BOOK_DELIVERY_FEES_PER_DAY = 10;

    @Override
    public Borrowing borrowBook(Long bookId, Long patronId, LocalDate dateMustReturnIn) {
        Book book = bookServiceInterface.findById(bookId);
        Patron patron = patronServiceInterface.findById(patronId);

        Borrowing borrowing = Borrowing.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .dateMustReturnIn(dateMustReturnIn)
                .actualReturnDate(null)
                .build();

        return borrowingRepo.save(borrowing);
    }

    @Override
    public Borrowing returnBook(Long bookId, Long patronId, LocalDate actualReturnDate) {
        Book book = bookServiceInterface.findById(bookId);
        Patron patron = patronServiceInterface.findById(patronId);

        Borrowing borrowing = borrowingRepo.findBorrowingByPatronAndBook(patron,book)
                        .orElseThrow();

        borrowing.setActualReturnDate(actualReturnDate);
        borrowingRepo.save(borrowing);


        int delayFeesMustBePaid = 0;
        if(actualReturnDate.isAfter(borrowing.getDateMustReturnIn())) {
            int delayDays = Period.between(borrowing.getDateMustReturnIn(), actualReturnDate).getDays();
            delayFeesMustBePaid = delayDays * BOOK_DELIVERY_FEES_PER_DAY;
        }

        borrowing.setPaidFeesAmount(delayFeesMustBePaid);
        borrowing = borrowingRepo.save(borrowing);

        return borrowing;
    }
}

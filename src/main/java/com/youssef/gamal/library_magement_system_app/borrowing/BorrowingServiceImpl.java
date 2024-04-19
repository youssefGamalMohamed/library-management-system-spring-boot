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
    public void borrowBook(Long bookId, Long patronId, LocalDate dateMustReturnIn) {
        Book book = bookServiceInterface.findById(bookId);
        Patron patron = patronServiceInterface.findById(patronId);

        Borrowing borrowing = Borrowing.builder()
                .borrowingCompositeKey(
                        BorrowingCompositeKey.builder()
                                .bookId(bookId)
                                .patronId(patronId)
                                .build()
                )
                .book(book)
                .patron(patron)
                .borrowingDate(LocalDate.now())
                .dateMustReturnIn(dateMustReturnIn)
                .actualReturnDate(null)
                .build();

        borrowingRepo.save(borrowing);
    }

    @Override
    public int returnBook(Long bookId, Long patronId, LocalDate actualReturnDate) {
        BorrowingCompositeKey borrowingId = BorrowingCompositeKey.builder()
                .bookId(bookId)
                .patronId(patronId)
                .build();

        Borrowing borrowing = borrowingRepo.findById(borrowingId)
                .orElseThrow();

        borrowing.setActualReturnDate(actualReturnDate);
        borrowingRepo.save(borrowing);


        int delayFeesMustBePaid = 0;
        if(actualReturnDate.isAfter(borrowing.getDateMustReturnIn())) {
            int delayDays = Period.between(borrowing.getDateMustReturnIn(), actualReturnDate).getDays();
            delayFeesMustBePaid = delayDays * BOOK_DELIVERY_FEES_PER_DAY;
        }

        return delayFeesMustBePaid;
    }
}

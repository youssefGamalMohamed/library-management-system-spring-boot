package com.youssef.gamal.library_magement_system_app.borrowing;

import java.time.LocalDate;

public interface BorrowingServiceInterface {


    Borrowing borrowBook(Long bookId, Long patronId, LocalDate dateMustReturnIn);

    Borrowing returnBook(Long bookId, Long patronId, LocalDate actualReturnDate);
}

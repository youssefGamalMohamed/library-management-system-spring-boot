package com.youssef.gamal.library_magement_system_app.borrowing;

import java.time.LocalDate;

public interface BorrowingServiceInterface {


    void borrowBook(Long bookId, Long patronId, LocalDate dateMustReturnIn);

    int returnBook(Long bookId, Long patronId, LocalDate actualReturnDate);
}

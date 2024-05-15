package com.youssef.gamal.library_magement_system_app.borrowing;

import com.youssef.gamal.library_magement_system_app.book.BookDto;
import com.youssef.gamal.library_magement_system_app.patron.PatronDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Borrowing", description = "Borrowing Model")
public class BorrowingDto {

    private Long id;

    private PatronDto patron;

    private BookDto book;

    private LocalDate borrowingDate;

    @Future(message = "Return Date must be in the Future(Tomorrow or After That)")
    private LocalDate dateMustReturnIn;

    private LocalDate actualReturnDate;

    private Integer paidFeesAmount;

}

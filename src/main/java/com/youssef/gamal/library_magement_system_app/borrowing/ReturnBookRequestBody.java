package com.youssef.gamal.library_magement_system_app.borrowing;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnBookRequestBody {

    @FutureOrPresent(message = "Return Date cannot be in the past")
    private LocalDate returnDate;

}

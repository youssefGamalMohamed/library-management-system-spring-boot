package com.youssef.gamal.library_magement_system_app.borrowing;


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
public class BorrowingRequestBody {

    @Future(message = "Return Date must be in the Future(Tomorrow or After That)")
    private LocalDate dateMustReturnIn;

}

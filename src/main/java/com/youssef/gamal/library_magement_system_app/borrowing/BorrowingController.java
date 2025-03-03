package com.youssef.gamal.library_magement_system_app.borrowing;

import com.youssef.gamal.library_magement_system_app.exceptions.ErrorValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@Tag(name = "Borrowing", description = "Borrowing API Endpoints")
public class BorrowingController {

    private final BorrowingServiceInterface borrowingServiceInterface;


    @Operation(summary = "Borrow Book", description = "Borrow Book for Patron")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Borrow Added Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BorrowingDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Validation Error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorValidationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Book Id Or Patron Id Not Found"
                    )
            }
    )

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable("bookId")  Long bookId,
                                    @PathVariable("patronId")  Long patronId,
                                    @Valid @RequestBody BorrowingDto requestBody) {
        
        Borrowing borrowing = borrowingServiceInterface.borrowBook(bookId, patronId, requestBody.getDateMustReturnIn());
        BorrowingDto responseBody = BorrowingMapper.toDto(borrowing);
        return ResponseEntity.ok()
                .body(responseBody);
    }



    @Operation(summary = "Return Book", description = "Return Book from Patron")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Book Returned Successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Validation Error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorValidationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Book Id Or Patron Id Not Found"
                    )
            }
    )

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable("bookId")  Long bookId,
                                        @PathVariable("patronId")  Long patronId) {

        LocalDate returnDate = LocalDate.now();

        Borrowing borrowing = borrowingServiceInterface.returnBook(bookId, patronId, returnDate);
        BorrowingDto responseBody = BorrowingMapper.toDto(borrowing);

        return ResponseEntity.ok()
                .body(responseBody);
    }
}

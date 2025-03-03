package com.youssef.gamal.library_magement_system_app.borrowing;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.youssef.gamal.library_magement_system_app.book.Book;
import com.youssef.gamal.library_magement_system_app.book.BookServiceInterface;
import com.youssef.gamal.library_magement_system_app.patron.Patron;
import com.youssef.gamal.library_magement_system_app.patron.PatronServiceInterface;

@WebMvcTest(controllers = BorrowingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BorrowingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingServiceInterface borrowingService;

    @MockBean
    private BookServiceInterface bookService;

    @MockBean
    private PatronServiceInterface patronService;

    @Test
    void borrowBook_shouldReturnOk_whenBookAndPatronExists() throws Exception {
        Long bookId = 1L;
        Long patronId = 2L;
        LocalDate dateMustReturnIn = LocalDate.now().plusDays(3);
        BorrowingDto requestBody = BorrowingDto.builder()
                .dateMustReturnIn(dateMustReturnIn)
                .build();

        Book book = Book.builder().id(bookId).build();
        Patron patron = Patron.builder().id(patronId).build();
        Borrowing borrowing = Borrowing.builder()
                .dateMustReturnIn(dateMustReturnIn)
                .book(book)
                .patron(patron)
                .build();

        when(bookService.findById(bookId)).thenReturn(book);
        when(patronService.findById(patronId)).thenReturn(patron);
        when(borrowingService.borrowBook(bookId, patronId, dateMustReturnIn)).thenReturn(borrowing);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ResultActions result = mockMvc.perform(post("/v1/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)));

        result.andExpect(status().isOk());
    }

    @Test
    void borrowBook_shouldReturnNotFound_whenBookOrPatronNotFound() throws Exception {
        Long bookId = 1L;
        Long patronId = 2L;
        LocalDate dateMustReturnIn = LocalDate.now().plusDays(3);
        BorrowingDto requestBody = BorrowingDto.builder()
                .dateMustReturnIn(dateMustReturnIn)
                .build();

        doThrow(NoSuchElementException.class).when(borrowingService).borrowBook(any(), any(), any());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ResultActions result = mockMvc.perform(post("/v1/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)));

        result.andExpect(status().isNotFound());
    }

    @Test
    void returnBook_shouldReturnOk_whenSuccessful() throws Exception {
        Long bookId = 123L;
        Long patronId = 456L;
        LocalDate returnDate = LocalDate.of(2025, 1, 1);
        BorrowingDto requestBody = BorrowingDto.builder()
                .actualReturnDate(returnDate)
                .build();

        Book book = Book.builder().id(bookId).build();
        Patron patron = Patron.builder().id(patronId).build();
        Borrowing borrowing = Borrowing.builder()
                .patron(patron)
                .book(book)
                .paidFeesAmount(0)
                .build();

        when(bookService.findById(bookId)).thenReturn(book);
        when(patronService.findById(patronId)).thenReturn(patron);
        when(borrowingService.returnBook(anyLong(), anyLong(), any(LocalDate.class))).thenReturn(borrowing);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc.perform(put("/v1/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paidFeesAmount").value(0));
    }

    @Test
    void returnBook_shouldReturnNotFound_whenBorrowingNotFound() throws Exception {
        Long bookId = 123L;
        Long patronId = 456L;
        LocalDate returnDate = LocalDate.of(2025, 1, 1);
        BorrowingDto requestBody = BorrowingDto.builder()
                .actualReturnDate(returnDate)
                .build();

        given(borrowingService.returnBook(anyLong(), anyLong(), any(LocalDate.class))).willThrow(new NoSuchElementException());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ResultActions result = mockMvc.perform(put("/v1/return/{bookId}/patron/{patronId}", bookId, patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)));

        result.andExpect(status().isNotFound());
    }
}

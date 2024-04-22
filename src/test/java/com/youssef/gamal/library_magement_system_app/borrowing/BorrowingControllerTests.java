package com.youssef.gamal.library_magement_system_app.borrowing;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BorrowingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BorrowingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingServiceInterface borrowingServiceInterface;

    @Test
    void borrowBook_shouldReturnOk_whenBookAndPatronExists() throws Exception {


        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        LocalDate dateMustReturnIn = LocalDate.now().plusDays(3);
        BorrowingRequestBody requestBody = BorrowingRequestBody.builder()
                .dateMustReturnIn(dateMustReturnIn)
                .build();

        // Act
        borrowingServiceInterface.borrowBook(bookId, patronId, dateMustReturnIn);


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // to handle java time module in ObjectMapper
        ResultActions result = mockMvc.perform(post("/v1/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)));

        // Assert
        result.andExpect(status().isOk());
    }

    @Test
    void borrowBook_shouldReturnBadRequest_whenBookOrPatronNotFound() throws Exception {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        LocalDate dateMustReturnIn = LocalDate.now().plusDays(3);
        BorrowingRequestBody requestBody = new BorrowingRequestBody(dateMustReturnIn);


        doThrow(NoSuchElementException.class)
                .when(borrowingServiceInterface)
                .borrowBook(any(), any(), any());


        // Act
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // to handle java time module in ObjectMapper
        ResultActions result = mockMvc.perform(post("/v1/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestBody)));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void testReturnBook_Successful() throws Exception {
        // Mock the borrowingServiceInterface method
        when(borrowingServiceInterface.returnBook(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(0);

        Long bookId = 123L;
        Long patronId = 456L;
        LocalDate returnDate = LocalDate.of(2025, 1, 1);
        ReturnBookRequestBody requestBody = ReturnBookRequestBody.builder()
                .returnDate(returnDate)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // to handle java time module in ObjectMapper

        // Perform the PUT request
        mockMvc.perform(put("/v1/return/"+ bookId + "/patron/" + patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delayFeesMustBePaid").value(0));
    }

}

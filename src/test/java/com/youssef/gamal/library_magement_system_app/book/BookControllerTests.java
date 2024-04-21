package com.youssef.gamal.library_magement_system_app.book;


import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.BDDMockito.given;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Year;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void BookController_AddBook_ReturnCreated() throws Exception {
        // Arrange
        AddBookRequestBody requestBody = AddBookRequestBody.builder()
                .description("Description 1")
                .author("Author 1")
                .title("Title 1")
                .publicationYear(Year.of(2020))
                .isbn("9780134685991")
                .build();

        Book book = BookMapper.toEntity(requestBody);
        given(bookServiceImpl.save(ArgumentMatchers.any(Book.class)))
                .willAnswer((invocation -> book.getId()));

        // Act
        ResultActions response = mockMvc.perform(post("/v1/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)));


        // Assert
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }



    @Test
    public void BookController_FindById_ReturnResponseCode200WithResponseOfBook() throws Exception {
        // Arrange
        long bookId = 1L;
        Book book = Book.builder()
                .id(bookId)
                .isbn("9780134685991")
                .publicationYear(Year.of(2020))
                .description("Description 1")
                .author("Author 1")
                .build();

        FindBookByIdResponseBody responseBody = FindBookByIdResponseBody.builder()
                .isbn(book.getIsbn())
                .publicationYear(String.valueOf(book.getPublicationYear().getValue()))
                .description(book.getDescription())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();

        // Mock the behavior of findById method
        when(bookServiceImpl.findById(bookId)).thenReturn(book);

        // Act
        ResultActions response = mockMvc.perform(get("/v1/book/" + bookId)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(responseBody)));
    }



    @Test
    public void BookController_FindByIdWithNonExistentId_ReturnResponseCode200WithResponseOfBook() throws Exception {
        // Arrange
        long bookId = 1L;
        Book book = Book.builder()
                .id(bookId)
                .isbn("9780134685991")
                .publicationYear(Year.of(2020))
                .description("Description 1")
                .author("Author 1")
                .title("Title 1")
                .build();

        FindBookByIdResponseBody responseBody = FindBookByIdResponseBody.builder()
                .isbn(book.getIsbn())
                .publicationYear(String.valueOf(book.getPublicationYear().getValue()))
                .description(book.getDescription())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
        when(bookServiceImpl.findById(bookId)).thenReturn(book);
        when(bookServiceImpl.findById(2L)).thenThrow(new NoSuchElementException("Book not found"));

        // Act
        ResultActions response = mockMvc.perform(get("/v1/book/" + 2)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

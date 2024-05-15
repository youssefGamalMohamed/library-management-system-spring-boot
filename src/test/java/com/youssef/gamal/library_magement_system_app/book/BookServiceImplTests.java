package com.youssef.gamal.library_magement_system_app.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTests {


    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @Test
    @DisplayName("Test if book can be saved correctly to the database and returned back the id of the saved book")
    void BookServiceImpl_AddBook_ReturnsBookId() {

        //Arrange
        Book book = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        when(bookRepo.save(any(Book.class)))
                .thenReturn(book);

        //Act
        Long bookId = bookServiceImpl.save(book).getId();

        //Assert
        Assertions.assertThat(bookId).isNotNull();
        Assertions.assertThat(bookId).isGreaterThan(0L);
    }



    @Test
    @DisplayName("Test if book returned from db by id")
    void BookServiceImpl_FindById_ReturnsBook() {
        //Arrange
        Book mockBook = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        when(bookRepo.findById(any(Long.class)))
                .thenReturn(Optional.of(mockBook));


        //Act
        Book book = null;
        try {
            book = bookServiceImpl.findById(1L);
        }
        catch (NoSuchElementException noSuchElementException) {

        }

        //Assert
        Assertions.assertThat(book).isNotNull();
    }


    @Test
    @DisplayName("Test if book not-returned from db by id when id does not exists in the db")
    void BookServiceImpl_FindByNonExistId_ReturnsNull() {

        //Arrange
        when(bookRepo.findById(any(Long.class)))
                .thenReturn(Optional.empty());


        //Act
        Book book = null;
        try {
            book = bookServiceImpl.findById(1L);
        }
        catch (NoSuchElementException noSuchElementException) {

        }

        //Assert
        Assertions.assertThat(book).isNull();
    }

    @Test
    @DisplayName("Test if book all books returned from db")
    void BookServiceImpl_FindAll_ReturnsAllBooks() {

        //Arrange
        Book mockBook1 = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        Book mockBook2 = Book.builder()
                .id(2L)
                .title("Book 2")
                .author("Author 2")
                .description("Description 2")
                .isbn("ISBN 2")
                .publicationYear(Year.of(2021))
                .build();

        when(bookRepo.findAll())
                .thenReturn(List.of(mockBook1,mockBook2));

        // Act
        List<Book> books = bookServiceImpl.findAll();

        // Assert
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books).hasSize(2);
    }

    @Test
    void BookServiceImpl_DeleteById_ReturnVoid() {
        //Arrange
        Book book = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        when(bookRepo.findById(any(Long.class)))
                .thenReturn(Optional.of(book));

        doNothing().when(bookRepo).deleteById(book.getId());

        //Act
        assertAll(() -> bookServiceImpl.deleteById(book.getId()));

    }

    @Test
    void BookServiceImpl_UpdateById_ReturnsVoid() {
        //Arrange
        Book book = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        when(bookRepo.findById(any(Long.class)))
                .thenReturn(Optional.of(book));

        when(bookRepo.save(any(Book.class)))
                .thenReturn(book);

        // Act
        System.out.println("book Before update = " + book);

        Book bookToUpdate = bookRepo.findById(book.getId()).get();
        bookToUpdate.setTitle("Book 2");
        bookToUpdate.setDescription("Description 2");
        bookToUpdate.setIsbn("ISBN 2");
        bookToUpdate.setAuthor("Author 2");
        bookToUpdate.setPublicationYear(Year.of(2021));

        bookToUpdate = bookRepo.save(bookToUpdate);

        System.out.println("book after update = " + bookToUpdate);


        // Assert
        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book).isEqualTo(bookToUpdate);
    }

    @Test
    void BookServiceImpl_UpdateByNonExistsId_ReturnsVoid() {
        //Arrange
        Book book = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        when(bookRepo.findById(any(Long.class)))
                .thenReturn(Optional.empty());


        // Act
        System.out.println("book Before update = " + book);

        Book bookToUpdate = null ;
        try {
            bookToUpdate = bookRepo.findById(2L).get();
        }
        catch (NoSuchElementException ex) {

        }

        // Assert
        Assertions.assertThat(bookToUpdate).isNull();
    }
}

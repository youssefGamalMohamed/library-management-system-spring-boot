package com.youssef.gamal.library_magement_system_app.book;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepoTests {

    @Autowired
    private BookRepo bookRepo;


    @Test
    @DisplayName("Test if book can be saved correctly to the database")
    public void BookRepo_SaveBook_ReturnSavedBook() {

        //Arrange
        Book book = Book.builder()
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();

        //Act
        Book savedBook = bookRepo.save(book);

        //Assert
        Assertions.assertThat(savedBook).isNotNull();
        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getId()).isGreaterThan(0L);
    }

    @Test
    @DisplayName("Test if book exists with given id in the db")
    public void BookRepo_FindById_ReturnSavedBook() {

        //Arrange
        Book book = Book.builder()
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        book = bookRepo.save(book);

        //Act
        Optional<Book> bookMayBeExistOrNot = bookRepo.findById(book.getId());

        //Assert
        Assertions.assertThat(bookMayBeExistOrNot).isNotEmpty();
        Assertions.assertThat(bookMayBeExistOrNot.get()).isNotNull();
        Assertions.assertThat(bookMayBeExistOrNot.get().getId()).isNotNull();
        Assertions.assertThat(bookMayBeExistOrNot.get().getId()).isGreaterThan(0L);
    }


    @Test
    @DisplayName("Test if all books returned successfully from db")
    public void BookRepo_FindAll_ReturnAllSavedBook() {

        //Arrange
        Book book1 = Book.builder()
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();

        Book book2 = Book.builder()
                .title("Book 2")
                .author("Author 2")
                .description("Description 2")
                .isbn("ISBN 2")
                .publicationYear(Year.of(2020))
                .build();
        bookRepo.saveAll(List.of(book1, book2));


        //Act
        List<Book> allBooks = bookRepo.findAll();

        //Assert
        Assertions.assertThat(allBooks).isNotNull();
        Assertions.assertThat(allBooks.size()).isGreaterThan(0);
    }


    @Test
    @DisplayName("Test if deleting book and find it by id will be empty from db")
    public void BookRepo_DeleteById_ReturnEmpty() {

        //Arrange
        Book book = Book.builder()
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        book = bookRepo.save(book);


        //Act
        bookRepo.deleteById(book.getId());
        Optional<Book> bookMayBeExistOrNot = bookRepo.findById(book.getId());


        //Assert
        Assertions.assertThat(bookMayBeExistOrNot).isEmpty();
    }



    @Test
    @DisplayName("Test if update book values will be updated successfully in db")
    public void BookRepo_UpdateBookById_ReturnUpdatedBook() {

        //Arrange
        Book book = Book.builder()
                .title("Book 1")
                .author("Author 1")
                .description("Description 1")
                .isbn("ISBN 1")
                .publicationYear(Year.of(2020))
                .build();
        Book newSavedBook = bookRepo.save(book);


        //Act
        newSavedBook.setTitle("Book 1 Updated");
        newSavedBook.setAuthor("Author 1 Updated");
        newSavedBook.setDescription("Description 1 Updated");
        newSavedBook.setPublicationYear(Year.of(2021));
        newSavedBook.setIsbn("ISBN 1 Updated");

        Book updatedBook = bookRepo.save(newSavedBook);


        //Assert
        Assertions.assertThat(updatedBook).isNotNull();
        Assertions.assertThat(updatedBook).isEqualTo(book);


    }
}

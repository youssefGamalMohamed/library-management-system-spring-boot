package com.youssef.gamal.library_magement_system_app.book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static Book toEntity(AddBookRequestBody requestBody) {
        return Book.builder()
                .title(requestBody.getTitle())
                .description(requestBody.getDescription())
                .publicationYear(requestBody.getPublicationYear())
                .isbn(requestBody.getIsbn())
                .author(requestBody.getAuthor())
                .build();
    }

    public static Book toEntity(UpdateBookRequestBody requestBody) {
        return Book.builder()
                .title(requestBody.getTitle())
                .description(requestBody.getDescription())
                .publicationYear(requestBody.getPublicationYear())
                .isbn(requestBody.getIsbn())
                .author(requestBody.getAuthor())
                .build();
    }
    public static AddBookResponseBody toAddBookResponseBody(Long bookId) {
        return AddBookResponseBody.builder()
                .id(bookId)
                .build();
    }

    public static FindBookByIdResponseBody toFindBookByIdResponseBody(Book book) {
        return FindBookByIdResponseBody.builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .publicationYear(book.getPublicationYear().toString())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .build();
    }

    public static FindAllBooksResponseBody toFindAllBooksResponseBody(List<Book> books) {
        return FindAllBooksResponseBody.builder()
                .books(
                    books.stream()
                        .map(BookMapper::toFindBookByIdResponseBody)
                        .collect(Collectors.toList())
                )
                .build();
    }
}

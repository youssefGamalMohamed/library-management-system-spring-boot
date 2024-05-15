package com.youssef.gamal.library_magement_system_app.book;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static Book toEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .description(bookDto.getDescription())
                .publicationYear(bookDto.getPublicationYear())
                .isbn(bookDto.getIsbn())
                .author(bookDto.getAuthor())
                .build();
    }

    public static BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .publicationYear(book.getPublicationYear())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .build();
    }
}

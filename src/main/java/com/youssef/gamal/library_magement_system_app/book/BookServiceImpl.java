package com.youssef.gamal.library_magement_system_app.book;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BookServiceImpl implements BookServiceInterface {


    private final BookRepo bookRepo;

    @Override
    public Long save(Book book) {
        return bookRepo.save(book).getId();
    }

    @Override
    public Book findById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepo.findById(id)
                        .orElseThrow();
        bookRepo.deleteById(book.getId());
    }

    @Override
    public void updateById(Long id, Book updatedBook) {
        Book book = bookRepo.findById(id)
                .orElseThrow();

        book.setTitle(updatedBook.getTitle());
        book.setDescription(updatedBook.getDescription());
        book.setIsbn(updatedBook.getIsbn());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());

        bookRepo.save(book);
    }


}

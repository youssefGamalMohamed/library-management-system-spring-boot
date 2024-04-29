package com.youssef.gamal.library_magement_system_app.book;


import com.youssef.gamal.library_magement_system_app.config.CachingConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BookServiceImpl implements BookServiceInterface {


    private final BookRepo bookRepo;

    @Override
    public Long save(Book book) {
        return bookRepo.save(book).getId();
    }

    @Cacheable(cacheNames = CachingConfig.BOOKS_CACHE_NAME, key = "#id")
    @Override
    public Book findById(Long id) {
        log.info("findById(id:{})", id);
        return bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
    }

    @Override
    public List<Book> findAll() {
        log.info("findAll()");
        return bookRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        log.info("deleteById(id:{})", id);

        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id + " to delete it"));

        bookRepo.deleteById(book.getId());
    }

    @Override
    public void updateById(Long id, Book updatedBook) {
        log.info("updateById(id:{}, updatedBook:{})", id, updatedBook);
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id + " to update it"));

        book.setTitle(updatedBook.getTitle());
        book.setDescription(updatedBook.getDescription());
        book.setIsbn(updatedBook.getIsbn());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());

        bookRepo.save(book);
    }


}

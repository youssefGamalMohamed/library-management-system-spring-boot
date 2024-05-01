package com.youssef.gamal.library_magement_system_app.book;


import com.youssef.gamal.library_magement_system_app.config.CachingConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Override
    @Cacheable(cacheNames = CachingConfig.BOOKS_CACHE_NAME, key = "#id") // to cache the object with the given id
    public Book findById(Long id) {
        log.info("findById(id:{})", id);
        return bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
    }


    @Override
    @Cacheable(cacheNames = CachingConfig.BOOKS_CACHE_NAME, key = "#root.methodName")
    public List<Book> findAll() {
        log.info("findAll()");
        return bookRepo.findAll();
    }

    @Override
    @CacheEvict(cacheNames = CachingConfig.BOOKS_CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        log.info("deleteById(id:{})", id);
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id + " to delete it"));

        bookRepo.deleteById(book.getId());
    }

    @Override
    @CachePut(cacheNames = CachingConfig.BOOKS_CACHE_NAME, key = "#id")
    public Book updateById(Long id, Book newBookInfoToUpdateWithIt) {
        log.info("updateById(id:{}, updatedBook:{})", id, newBookInfoToUpdateWithIt);
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id + " to update it"));

        book.setTitle(newBookInfoToUpdateWithIt.getTitle());
        book.setDescription(newBookInfoToUpdateWithIt.getDescription());
        book.setIsbn(newBookInfoToUpdateWithIt.getIsbn());
        book.setAuthor(newBookInfoToUpdateWithIt.getAuthor());
        book.setPublicationYear(newBookInfoToUpdateWithIt.getPublicationYear());

        book = bookRepo.save(book);
        return book;
    }


}

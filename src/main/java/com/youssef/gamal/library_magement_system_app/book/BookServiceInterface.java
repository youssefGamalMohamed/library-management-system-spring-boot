package com.youssef.gamal.library_magement_system_app.book;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


public interface BookServiceInterface {

    Long save(Book book);

    Book findById(Long id);

    List<Book> findAll();

    void deleteById(Long id);

    Book updateById(Long id, Book newBookInfoToUpdateWithIt);
}

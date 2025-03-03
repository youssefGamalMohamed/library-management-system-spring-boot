package com.youssef.gamal.library_magement_system_app.book;

import java.util.List;


public interface BookServiceInterface {

    Book save(Book book);

    Book findById(Long id);

    List<Book> findAll();

    void deleteById(Long id);

    Book updateById(Long id, Book newBookInfoToUpdateWithIt);
}

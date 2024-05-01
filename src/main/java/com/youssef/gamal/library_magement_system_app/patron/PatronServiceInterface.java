package com.youssef.gamal.library_magement_system_app.patron;


import java.util.List;

public interface PatronServiceInterface {

    Long add(Patron patron);

    Patron findById(Long id);

    List<Patron> findAll();

    void deleteById(Long id);

    Patron updateById(Long id, Patron updatedPatron);
}

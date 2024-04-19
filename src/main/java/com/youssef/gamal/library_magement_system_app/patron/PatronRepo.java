package com.youssef.gamal.library_magement_system_app.patron;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {
}

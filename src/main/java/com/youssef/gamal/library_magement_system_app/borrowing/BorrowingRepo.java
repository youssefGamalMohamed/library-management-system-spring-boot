package com.youssef.gamal.library_magement_system_app.borrowing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRepo extends JpaRepository<Borrowing, BorrowingCompositeKey> {

}

package com.youssef.gamal.library_magement_system_app.borrowing;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BorrowingCompositeKey implements Serializable {

    @Column(name = "patron_id")
    private Long patronId;

    @Column(name = "book_id")
    private Long bookId;
}

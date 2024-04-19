package com.youssef.gamal.library_magement_system_app.book;

import com.youssef.gamal.library_magement_system_app.borrowing.Borrowing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Year publicationYear;

    private String isbn;

    private String author;

    @OneToMany(mappedBy = "book" , cascade = CascadeType.ALL)
    private List<Borrowing> borrowings;
}

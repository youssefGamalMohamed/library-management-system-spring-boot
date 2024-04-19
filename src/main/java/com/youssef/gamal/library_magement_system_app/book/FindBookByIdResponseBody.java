package com.youssef.gamal.library_magement_system_app.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindBookByIdResponseBody {

    private String title;

    private String description;

    private String publicationYear;

    private String isbn;

    private String author;
}

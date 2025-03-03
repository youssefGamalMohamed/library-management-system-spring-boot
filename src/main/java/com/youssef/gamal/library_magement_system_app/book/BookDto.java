package com.youssef.gamal.library_magement_system_app.book;

import java.time.Year;

import org.hibernate.validator.constraints.ISBN;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "Book", description = "Book Model")
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null values during serialization
public class BookDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @PastOrPresent(message = "Publication date cannot be in the future")
    @NotNull(message = "Publication year is required")
    @Schema(example = "2020", type = "integer")
    private Year publicationYear;

    @NotBlank(message = "ISBN is required")
    @ISBN(message = "Invalid ISBN")
    @Schema(example = "9780134685991")
    private String isbn;


    @NotBlank(message = "Author is required")
    private String author;

}

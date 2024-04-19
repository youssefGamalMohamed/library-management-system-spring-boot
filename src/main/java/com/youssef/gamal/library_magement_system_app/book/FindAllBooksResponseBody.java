package com.youssef.gamal.library_magement_system_app.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllBooksResponseBody {

    private List<FindBookByIdResponseBody> books;

}

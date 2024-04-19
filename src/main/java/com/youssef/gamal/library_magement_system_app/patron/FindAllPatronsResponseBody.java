package com.youssef.gamal.library_magement_system_app.patron;

import com.youssef.gamal.library_magement_system_app.book.FindBookByIdResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllPatronsResponseBody {

    private List<FindPatronByIdResponseBody> books;

}

package com.youssef.gamal.library_magement_system_app.patron;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPatronByIdResponseBody {

    private String name;

    private String email;

    private String phone;

    private String address;
}

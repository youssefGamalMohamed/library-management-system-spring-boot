package com.youssef.gamal.library_magement_system_app.patron;

import java.util.List;

public class PatronMapper {

    public static Patron toEntity(AddPatronRequestBody requestBody) {
        return Patron.builder()
            .name(requestBody.getName())
            .email(requestBody.getEmail())
            .phone(requestBody.getPhone())
            .address(requestBody.getAddress())
            .build();
    }

    public static Patron toEntity(UpdatePatronRequestBody requestBody) {
        return Patron.builder()
                .name(requestBody.getName())
                .email(requestBody.getEmail())
                .phone(requestBody.getPhone())
                .address(requestBody.getAddress())
                .build();
    }

    public static AddPatronResponseBody toAddPatronResponseBody(Long newPatronId) {
        return AddPatronResponseBody.builder()
                .id(newPatronId)
                .build();
    }

    public static FindPatronByIdResponseBody toFindPatronByIdResponseBody(Patron patron) {
        return FindPatronByIdResponseBody.builder()
                .name(patron.getName())
                .email(patron.getEmail())
                .phone(patron.getPhone())
                .address(patron.getAddress())
                .build();
    }

    public static FindAllPatronsResponseBody toFindAllPatronsResponseBody(List<Patron> patrons) {
        return FindAllPatronsResponseBody.builder()
                .books(
                        patrons.stream()
                                .map(PatronMapper::toFindPatronByIdResponseBody)
                                .toList()
                )
                .build();
    }
}

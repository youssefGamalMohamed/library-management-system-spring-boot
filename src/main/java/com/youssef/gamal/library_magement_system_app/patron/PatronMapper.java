package com.youssef.gamal.library_magement_system_app.patron;


public class PatronMapper {

    public static Patron toEntity(PatronDto patronDto) {
        return Patron.builder()
            .id(patronDto.getId())
            .name(patronDto.getName())
            .email(patronDto.getEmail())
            .phone(patronDto.getPhone())
            .address(patronDto.getAddress())
            .build();
    }

    public static PatronDto toDto(Patron patron) {
        return PatronDto.builder()
                .id(patron.getId())
                .name(patron.getName())
                .email(patron.getEmail())
                .phone(patron.getPhone())
                .address(patron.getAddress())
                .build();
    }


}

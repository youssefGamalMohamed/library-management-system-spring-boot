package com.youssef.gamal.library_magement_system_app.borrowing;

import com.youssef.gamal.library_magement_system_app.book.BookMapper;
import com.youssef.gamal.library_magement_system_app.patron.PatronMapper;

public class BorrowingMapper {
    public static BorrowingDto toDto(Borrowing borrowing) {
        return BorrowingDto.builder()
                .id(borrowing.getId())
                .patron(PatronMapper.toDto(borrowing.getPatron()))
                .book(BookMapper.toDto(borrowing.getBook()))
                .borrowingDate(borrowing.getBorrowingDate())
                .dateMustReturnIn(borrowing.getDateMustReturnIn())
                .actualReturnDate(borrowing.getActualReturnDate())
                .paidFeesAmount(borrowing.getPaidFeesAmount())
                .build();
    }

    public static Borrowing toEntity(BorrowingDto borrowingDto) {
        return Borrowing.builder()
                .id(borrowingDto.getId())
                .patron(PatronMapper.toEntity(borrowingDto.getPatron()))
                .book(BookMapper.toEntity(borrowingDto.getBook()))
                .borrowingDate(borrowingDto.getBorrowingDate())
                .dateMustReturnIn(borrowingDto.getDateMustReturnIn())
                .actualReturnDate(borrowingDto.getActualReturnDate())
                .paidFeesAmount(borrowingDto.getPaidFeesAmount())
                .build();
    }
}

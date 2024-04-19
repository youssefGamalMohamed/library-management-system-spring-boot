package com.youssef.gamal.library_magement_system_app.borrowing;

import com.youssef.gamal.library_magement_system_app.book.Book;
import com.youssef.gamal.library_magement_system_app.patron.Patron;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Borrowing {

    @EmbeddedId
    private BorrowingCompositeKey borrowingCompositeKey;

    @ManyToOne
    @MapsId("patronId")
    private Patron patron;


    @ManyToOne
    @MapsId("bookId")
    private Book book;

    private LocalDate borrowingDate;

    private LocalDate dateMustReturnIn;

    private LocalDate actualReturnDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Borrowing borrowing = (Borrowing) o;
        return getBorrowingCompositeKey() != null && Objects.equals(getBorrowingCompositeKey(), borrowing.getBorrowingCompositeKey());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(borrowingCompositeKey);
    }
}

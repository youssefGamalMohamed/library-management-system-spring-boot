package com.youssef.gamal.library_magement_system_app.patron;

import com.youssef.gamal.library_magement_system_app.borrowing.Borrowing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    @OneToMany(mappedBy = "patron" , cascade = CascadeType.ALL)
    private List<Borrowing> borrowings;

}

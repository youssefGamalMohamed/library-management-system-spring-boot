package com.youssef.gamal.library_magement_system_app.patron;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PatronRepoTests {


    @Autowired
    private PatronRepo patronRepo;

    @Test
    public void PatronRepo_SavedPatron_ReturnSavedPatron() {

        //Arrange
        Patron patron = Patron.builder()
                .name("youssef")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .address("Cairo")
                .build();

        //Act
        Patron savedPatron = patronRepo.save(patron);

        //Assert
        Assertions.assertThat(savedPatron).isNotNull();
        Assertions.assertThat(savedPatron.getId()).isNotNull();
        Assertions.assertThat(savedPatron.getId()).isGreaterThan(0L);
    }


    @Test
    public void PatronRepo_FindAll_ReturnAllSavedPatron() {

        //Arrange
        Patron patron1 = Patron.builder()
                .name("youssef")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .address("Cairo")
                .build();

        //Arrange
        Patron patron2 = Patron.builder()
                .name("ahmed")
                .phone("01123456789")
                .email("ahmed@gmail.com")
                .address("Cairo")
                .build();

        patronRepo.saveAll(List.of(patron1, patron2));


        //Act
        List<Patron> allPatrons = patronRepo.findAll();

        //Assert
        Assertions.assertThat(allPatrons).isNotNull();
        Assertions.assertThat(allPatrons.size()).isGreaterThan(0);
        Assertions.assertThat(allPatrons).hasSize(2);
    }


    @Test
    public void PatronRepo_FindById_ReturnSavedPatron() {

        //Arrange
        Patron patron = Patron.builder()
                .name("youssef")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .address("Cairo")
                .build();
        patronRepo.save(patron);

        //Act
        Optional<Patron> patronMayBeExistOrNot = patronRepo.findById(patron.getId());

        //Assert
        Assertions.assertThat(patronMayBeExistOrNot).isNotEmpty();
        Assertions.assertThat(patronMayBeExistOrNot.get()).isNotNull();
        Assertions.assertThat(patronMayBeExistOrNot.get().getId()).isNotNull();
        Assertions.assertThat(patronMayBeExistOrNot.get().getId()).isGreaterThan(0L);
    }


    @Test
    public void PatronRepo_DeleteById_ReturnEmpty() {
        //Arrange
        Patron patron = Patron.builder()
                .name("youssef")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .address("Cairo")
                .build();
        patron = patronRepo.save(patron);


        //Act
        patronRepo.deleteById(patron.getId());
        Optional<Patron> patronMayBeExistOrNot = patronRepo.findById(patron.getId());


        //Assert
        Assertions.assertThat(patronMayBeExistOrNot).isEmpty();
    }


    @Test
    public void PatronRepo_UpdatePatronById_ReturnUpdatedPatron() {

        //Arrange
        Patron patron = Patron.builder()
                .name("youssef")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .address("Cairo")
                .build();
        Patron newSavedaPatron = patronRepo.save(patron);


        //Act
        newSavedaPatron.setName("Ahmed");
        newSavedaPatron.setPhone("01123456789");
        newSavedaPatron.setEmail("ahmed@gmail.com");
        newSavedaPatron.setAddress("Tanta");

        Patron updatedPatron = patronRepo.save(newSavedaPatron);


        //Assert
        Assertions.assertThat(updatedPatron).isNotNull();
        Assertions.assertThat(updatedPatron).isEqualTo(patron);


    }

}

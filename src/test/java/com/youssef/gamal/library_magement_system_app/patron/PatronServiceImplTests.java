package com.youssef.gamal.library_magement_system_app.patron;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatronServiceImplTests {


    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronServiceImpl patronServiceImpl;

    @Test
    void PatronServiceImpl_AddPatron_ReturnsPatronId() {

        //Arrange
        Patron patron = Patron.builder()
                .id(1L)
                .name("youssef")
                .address("Cairo")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .build();

        when(patronRepo.save(any(Patron.class)))
                .thenReturn(patron);

        //Act
        Patron patronFromDb = patronServiceImpl.add(patron);

        //Assert
        Assertions.assertThat(patronFromDb).isNotNull();
        Assertions.assertThat(patronFromDb.getId()).isGreaterThan(0L);
    }


    @Test
    void PatronServiceImpl_FindById_ReturnsPatron() {
        //Arrange
        Patron mockPatron = Patron.builder()
                .id(1L)
                .name("youssef")
                .address("Cairo")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .build();

        when(patronRepo.findById(any(Long.class)))
                .thenReturn(Optional.of(mockPatron));


        //Act
        Patron patron = null;
        try {
            patron = patronServiceImpl.findById(1L);
        }
        catch (NoSuchElementException noSuchElementException) {

        }

        //Assert
        Assertions.assertThat(patron).isNotNull();
    }


    @Test
    void PatronServiceImpl_FindByNonExistId_ReturnsNull() {

        //Arrange
        when(patronRepo.findById(any(Long.class)))
                .thenReturn(Optional.empty());


        //Act
        Patron patron = null;
        try {
            patron = patronServiceImpl.findById(1L);
        }
        catch (NoSuchElementException noSuchElementException) {

        }

        //Assert
        Assertions.assertThat(patron).isNull();
    }


    @Test
    void PatronServiceImpl_FindAll_ReturnsAllPatrons() {
        //Arrange
        Patron patron1 = Patron.builder()
                .id(1L)
                .name("youssef")
                .address("Cairo")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .build();

        Patron patron2 = Patron.builder()
                .id(2L)
                .name("ahmed")
                .address("Cairo")
                .phone("0123456789")
                .email("ahmed@gmail.com")
                .build();

        when(patronRepo.findAll())
                .thenReturn(List.of(patron1,patron2));

        // Act
        List<Patron> patrons = patronServiceImpl.findAll();

        // Assert
        Assertions.assertThat(patrons).isNotNull();
        Assertions.assertThat(patrons).hasSize(2);
    }

    @Test
    void PatronServiceImpl_UpdateById_ReturnsVoid() {
        //Arrange
        Patron patron = Patron.builder()
                .id(1L)
                .name("youssef")
                .address("Cairo")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .build();
        when(patronRepo.findById(any(Long.class)))
                .thenReturn(Optional.of(patron));

        when(patronRepo.save(any(Patron.class)))
                .thenReturn(patron);

        // Act
        System.out.println("patron Before update = " + patron);

        Patron patronToUpdate = patronRepo.findById(patron.getId()).get();
        patronToUpdate.setName("Ahmed");
        patronToUpdate.setEmail("ahmed@gmail.com");
        patronToUpdate.setPhone("0123456788");
        patronToUpdate.setAddress("Cairo");

        patronToUpdate = patronRepo.save(patronToUpdate);

        System.out.println("patron after update = " + patronToUpdate);


        // Assert
        Assertions.assertThat(patron).isNotNull();
        Assertions.assertThat(patron).isEqualTo(patronToUpdate);
    }



    @Test
    void PatronServiceImpl_UpdateByNonExistsId_ReturnsVoid() {
        //Arrange
        Patron patron = Patron.builder()
                .id(1L)
                .name("youssef")
                .address("Cairo")
                .phone("0123456789")
                .email("youssef@gmail.com")
                .build();
        when(patronRepo.findById(any(Long.class)))
                .thenReturn(Optional.empty());


        // Act
        System.out.println("patron Before update = " + patron);

        Patron patronToUpdate = null ;
        try {
            patronToUpdate = patronRepo.findById(2L).get();
        }
        catch (NoSuchElementException ex) {

        }

        // Assert
        Assertions.assertThat(patronToUpdate).isNull();
    }
}

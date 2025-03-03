package com.youssef.gamal.library_magement_system_app.patron;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PatronController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PatronControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronServiceImpl patronServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void PatronController_AddPatron_ReturnCreated() throws Exception {
        // Arrange
        PatronDto requestBody = PatronDto.builder()
                .email("youssef@gmail")
                .name("youssef")
                .address("cairo")
                .phone("(+02) 01112345678")
                .build();

        Patron patron = PatronMapper.toEntity(requestBody);
        given(patronServiceImpl.add(ArgumentMatchers.any(Patron.class)))
                .willAnswer((invocation -> patron));

        // Act
        ResultActions response = mockMvc.perform(post("/v1/patron")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)));


        // Assert
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void PatronController_FindById_ReturnResponseCode200WithResponseOfPatron() throws Exception {
        // Arrange
        long patronId = 1L;
        Patron patron = Patron.builder()
                .id(patronId)
                .name("youssef")
                .email("youssef@gmail")
                .phone("(+02) 01112345678")
                .address("cairo")
                .build();

        PatronDto responseBody = PatronDto.builder()
                .id(patronId)
                .name(patron.getName())
                .email(patron.getEmail())
                .phone(patron.getPhone())
                .address(patron.getAddress())
                .build();

        // Mock the behavior of findById method
        when(patronServiceImpl.findById(patronId)).thenReturn(patron);

        // Act
        ResultActions response = mockMvc.perform(get("/v1/patron/" + patronId)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(responseBody)));
    }


    @Test
    public void PatronController_FindByIdWithNonExistentId_ReturnNotFound404() throws Exception {
        // Arrange
        long patronId = 1L;
        Patron patron = Patron.builder()
                .id(patronId)
                .name("youssef")
                .email("youssef@gmail")
                .phone("(+02) 01112345678")
                .address("cairo")
                .build();

        when(patronServiceImpl.findById(patronId)).thenReturn(patron);
        when(patronServiceImpl.findById(2L)).thenThrow(new NoSuchElementException("Patron not found"));

        // Act
        ResultActions response = mockMvc.perform(get("/v1/patron/" + 2)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

package com.youssef.gamal.library_magement_system_app.patron;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.gamal.library_magement_system_app.exceptions.ErrorValidationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/patron")
@Tag(name = "Patron", description = "Patron API Endpoints")
@AllArgsConstructor
@Slf4j
public class PatronController {

    private final PatronServiceInterface patronServiceInterface;

    @Operation(summary = "Add Patron", description = "Add Patron")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", description = "Book Added Successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PatronDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Validation Error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ErrorValidationResponse.class)
                                    )
                            }
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody PatronDto requestBody) {
        Patron newSavedPatron = patronServiceInterface.add(PatronMapper.toEntity(requestBody));
        PatronDto responseBody = PatronMapper.toDto(newSavedPatron);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }


    @Operation(summary = "Find Patron By Id", description = "Find Patron By Id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Patron Retrieved Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PatronDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Patron Not Found With the Given Id"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Patron patron = patronServiceInterface.findById(id);
        PatronDto responseBody = PatronMapper.toDto(patron);
        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Find All Patrons", description = "Find All Patrons")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "Patrons Retrieved Successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PatronDto.class)
                                    )
                            }
                    )
            }
    )

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Patron> patrons = patronServiceInterface.findAll();
        List<PatronDto> responseBody = patrons.stream()
                .map(PatronMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseBody);
    }


    @Operation(summary = "Delete Patron By Id", description = "Delete Patron By Id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204", description = "Patron Deleted Successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Patron Not Found With the Given Id"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        patronServiceInterface.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }


    @Operation(summary = "Update Patron By Id", description = "Update Patron By Id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204", description = "Patron Updated Successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Patron Not Found With the Given Id"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody PatronDto requestBody) {
        Patron patron = PatronMapper.toEntity(requestBody);
        Patron updatedPatron = patronServiceInterface.updateById(id, patron);
        log.info("Updated Patron: {}", updatedPatron);
        return ResponseEntity.noContent()
                .build();
    }
}

package com.youssef.gamal.library_magement_system_app.patron;

import com.youssef.gamal.library_magement_system_app.exceptions.ErrorValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patron")
@Tag(name = "Patron", description = "Patron API Endpoints")
@AllArgsConstructor
public class PatronController {

    private final PatronServiceInterface patronServiceInterface;

    @Operation(summary = "Add Patron", description = "Add Patron")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", description = "Book Added Successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AddPatronResponseBody.class)
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
    public ResponseEntity<?> add(@Valid @RequestBody AddPatronRequestBody requestBody) {
        Long newPatronId = patronServiceInterface.add(PatronMapper.toEntity(requestBody));
        AddPatronResponseBody responseBody = PatronMapper.toAddPatronResponseBody(newPatronId);
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
                                            schema = @Schema(implementation = FindPatronByIdResponseBody.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Patron Not Found With the Given Id"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Patron patron = patronServiceInterface.findById(id);
        FindPatronByIdResponseBody responseBody = PatronMapper.toFindPatronByIdResponseBody(patron);
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
                                            schema = @Schema(implementation = FindAllPatronsResponseBody.class)
                                    )
                            }
                    )
            }
    )

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Patron> patrons = patronServiceInterface.findAll();
        FindAllPatronsResponseBody responseBody = PatronMapper.toFindAllPatronsResponseBody(patrons);
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
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody UpdatePatronRequestBody requestBody) {
        Patron patron = PatronMapper.toEntity(requestBody);
        patronServiceInterface.updateById(id, patron);
        return ResponseEntity.noContent()
                .build();
    }
}

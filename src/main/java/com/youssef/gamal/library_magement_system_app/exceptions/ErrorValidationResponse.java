package com.youssef.gamal.library_magement_system_app.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorValidationResponse {
    /*
    * this attribute holds the validation errors as a map contains the field name as the key and the error as the value
    * */
    @Schema(description = "map of validation errors" , example = """
                {   
                    \"id\" : \"must not be null\",
                    \"name\" : \"must not be null\",
                    \"email\" : \"must not be null\"
                }    
            """)
    private Map<String,String> validationErrors;
}

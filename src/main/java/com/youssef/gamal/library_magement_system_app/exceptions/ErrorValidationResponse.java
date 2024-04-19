package com.youssef.gamal.library_magement_system_app.exceptions;

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
    private Map<String,String> validationErrors;
}

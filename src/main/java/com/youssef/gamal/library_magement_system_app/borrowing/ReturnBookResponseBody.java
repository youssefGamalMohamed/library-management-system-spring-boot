package com.youssef.gamal.library_magement_system_app.borrowing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnBookResponseBody {

    @Schema(description = "the total fees that the patron must pay when he/she returns the book after the return date has passed" +
            "ana calculate with (total delay days * total delay fees per day)", example = "10")
    private Integer delayFeesMustBePaid;

}

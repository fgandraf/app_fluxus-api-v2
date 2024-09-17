package com.felipegandra.app_fluxusapiv2.modules.invoices.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record InvoiceUpdateRequest(

        @NotNull(message = "Id is required.")
        Long id,

        @NotNull(message = "Description is required.")
        @Size(min = 1, max = 100, message = "Description must be between 1 and 100 characters.")
        String description,

        @NotNull(message = "Issue date is required.")
        LocalDateTime issueDate,

        @NotNull(message = "Subtotal Service is required.")
        Double subtotalService,

        @NotNull(message = "Subtotal Mileage is required.")
        Double subtotalMileage,

        @NotNull(message = "Total is required.")
        Double total
) {
}

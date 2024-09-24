package com.felipegandra.app_fluxusapiv2.modules.invoices.dtos;

import jakarta.validation.constraints.NotNull;

public record InvoiceUpdateRequest(

        @NotNull(message = "Id is required.")
        Long id,

        @NotNull(message = "Subtotal Service is required.")
        Double subtotalService,

        @NotNull(message = "Subtotal Mileage is required.")
        Double subtotalMileage,

        @NotNull(message = "Total is required.")
        Double total
) {
}

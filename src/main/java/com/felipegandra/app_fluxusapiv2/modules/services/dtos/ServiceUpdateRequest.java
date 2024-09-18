package com.felipegandra.app_fluxusapiv2.modules.services.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceUpdateRequest(

        @NotNull(message = "Id is required.")
        Long id,

        @NotNull(message = "Tag is required.")
        @Size(min = 1, max = 10, message = "Tag must be between 1 and 10 characters.")
        String tag,

        @NotNull(message = "Description is required.")
        @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters.")
        String description,

        @NotNull(message = "Service Amount is required.")
        Double serviceAmount,

        @NotNull(message = "Mileage Amount is required.")
        Double mileageAllowance
) {
}

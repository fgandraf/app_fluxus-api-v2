package com.felipegandra.app_fluxusapiv2.modules.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserConfigUpdateInput(

        @NotBlank(message = "Id is required!")
        Long id,

        Long professionalId,
        Boolean technicianResponsible,
        Boolean legalResponsible
) {
}
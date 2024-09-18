package com.felipegandra.app_fluxusapiv2.modules.users.dtos;

import jakarta.validation.constraints.NotNull;

public record UserUpdateConfigRequest(

        @NotNull(message = "User Id is required.")
        Long id,

        Long professionalId,

        @NotNull(message = "Technician Responsible is required.")
        Boolean technicianResponsible,

        @NotNull(message = "Legal Responsible is required.")
        Boolean legalResponsible
) {
}
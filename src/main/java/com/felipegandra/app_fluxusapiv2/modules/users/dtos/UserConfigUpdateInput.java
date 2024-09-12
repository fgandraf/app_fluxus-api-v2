package com.felipegandra.app_fluxusapiv2.modules.users.dtos;

public record UserConfigUpdateInput(

        Long id,

        Long professionalId,
        Boolean technicianResponsible,
        Boolean legalResponsible
) {
}
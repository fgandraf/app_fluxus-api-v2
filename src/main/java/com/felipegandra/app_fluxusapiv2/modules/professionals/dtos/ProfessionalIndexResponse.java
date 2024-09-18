package com.felipegandra.app_fluxusapiv2.modules.professionals.dtos;

public record ProfessionalIndexResponse(
        Long id,
        String tag,
        String name,
        String profession,
        String phone1
) {
}

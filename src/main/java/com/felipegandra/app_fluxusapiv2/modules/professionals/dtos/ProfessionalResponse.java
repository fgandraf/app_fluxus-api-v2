package com.felipegandra.app_fluxusapiv2.modules.professionals.dtos;

import com.felipegandra.app_fluxusapiv2.modules.professionals.Professional;
import java.time.LocalDate;

public record ProfessionalResponse(
        Long id,
        String tag,
        String name,
        String cpf,
        LocalDate birthday,
        String profession,
        String permitNumber,
        String association,
        String phone1,
        String phone2,
        String email
) {
    public ProfessionalResponse(Professional professional) {
        this(
                professional.getId(),
                professional.getTag(),
                professional.getName(),
                professional.getCpf(),
                professional.getBirthday(),
                professional.getProfession(),
                professional.getPermitNumber(),
                professional.getAssociation(),
                professional.getPhone1(),
                professional.getPhone2(),
                professional.getEmail()
        );
    }
}
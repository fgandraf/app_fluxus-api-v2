package com.felipegandra.app_fluxusapiv2.modules.professionals.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProfessionalCreateRequest(

        @Size(max = 5, message = "Tag must have a maximum of 5 characters")
        String tag,

        @NotNull(message = "Name is required.")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.")
        String name,

        @Size(max = 11, message = "Cpf must have a maximum of 1 characters")
        String cpf,
        
        LocalDate birthday,

        @NotNull(message = "Profession is required.")
        @Size(min = 1, max = 50, message = "Profesion must be between 1 and 50 characters.")
        String profession,

        @NotNull(message = "Permit number is required.")
        @Size(min = 1, max = 50, message = "Permit number must be between 1 and 50 characters.")
        String permitNumber,

        @NotNull(message = "Association is required.")
        @Size(min = 1, max = 100, message = "Association must be between 1 and 100 characters.")
        String association,

        @Size(max = 11, message = "Phone1 must have a maximum of 1 characters")
        String phone1,

        @Size(max = 11, message = "Phone2 must have a maximum of 1 characters")
        String phone2,

        @Size(max = 100, message = "Email must have a maximum of 1 characters")
        String email
) {
}

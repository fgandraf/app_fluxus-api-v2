package com.felipegandra.app_fluxusapiv2.modules.branches.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BranchUpdateRequest(

        @NotNull(message = "Id is required.")
        @Size(min = 1, max = 4, message = "Id must be between 1 and 4 characters.")
        String id,

        @NotNull(message = "Name is required.")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.")
        String name,

        @Size(max = 255, message = "Address must have a maximum of 255 characters")
        String address,

        @Size(max = 255, message = "Complement must have a maximum of 255 characters")
        String complement,

        @Size(max = 100, message = "District must have a maximum of 100 characters")
        String district,

        @Size(max = 100, message = "City must have a maximum of 100 characters")
        String city,

        @Size(max = 8, message = "District must have a maximum of 8 characters")
        String zip,

        @Size(max = 2, message = "State must have a maximum of 2 characters")
        String state,

        @Size(max = 100, message = "Contact name must have a maximum of 100 characters")
        String contactName,

        @Size(max = 11, message = "Phone1 must have a maximum of 11 characters")
        String phone1,

        @Size(max = 11, message = "Phone2 must have a maximum of 11 characters")
        String phone2,

        @Size(max = 100, message = "Email must have a maximum of 100 characters")
        String email
) {
}
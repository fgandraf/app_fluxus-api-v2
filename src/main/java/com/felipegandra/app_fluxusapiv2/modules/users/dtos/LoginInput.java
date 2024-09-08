package com.felipegandra.app_fluxusapiv2.modules.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginInput(

        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email address!")
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 4, max = 20, message = "Password must be between 4 -20  characters")
        String password
) {
}
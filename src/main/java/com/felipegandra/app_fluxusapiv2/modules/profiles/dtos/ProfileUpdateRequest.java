package com.felipegandra.app_fluxusapiv2.modules.profiles.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProfileUpdateRequest(

        @NotNull(message = "Id is required.")
        Long id,

        @Size(max = 14, message = "Anpj must have a maximum of 14 characters")
        String cnpj,

        @Size(max = 100, message = "Trading Name must have a maximum of 100 characters")
        String tradingName,

        @Size(max = 255, message = "Company Name must have a maximum of 255 characters")
        String companyName,

        @Size(max = 50, message = "State Id must have a maximum of 50 characters")
        String stateId,

        @Size(max = 50, message = "City Id must have a maximum of 50 characters")
        String cityId,

        @Size(max = 255, message = "Address must have a maximum of 255 characters")
        String address,

        @Size(max = 255, message = "Complement must have a maximum of 255 characters")
        String complement,

        @Size(max = 100, message = "District must have a maximum of 100 characters")
        String district,

        @Size(max = 100, message = "City must have a maximum of 100 characters")
        String city,

        @Size(max = 8, message = "Zip must have a maximum of 8 characters")
        String zip,

        @Size(max = 100, message = "State must have a maximum of 100 characters")
        String state,

        LocalDate establishmentDate,

        @Size(max = 11, message = "Phone1 must have a maximum of 11 characters")
        String phone1,

        @Size(max = 11, message = "Phone2 must have a maximum of 11 characters")
        String phone2,

        @Size(max = 100, message = "Email must have a maximum of 100 characters")
        String email,

        @Size(max = 100, message = "Bank Account Name must have a maximum of 100 characters")
        String bankAccountName,

        @Size(max = 50, message = "Bank Account Type must have a maximum of 50 characters")
        String bankAccountType,

        @Size(max = 10, message = "Bank Account Branch must have a maximum of 10 characters")
        String bankAccountBranch,

        @Size(max = 5, message = "Bank Account Digit must have a maximum of 5 characters")
        String bankAccountDigit,

        @Size(max = 20, message = "Bank Account Number must have a maximum of 20 characters")
        String bankAccountNumber,

        @Size(max = 100, message = "Contractor Name must have a maximum of 100 characters")
        String contractorName,

        @Size(max = 100, message = "Contract Notice must have a maximum of 100 characters")
        String contractNotice,

        @Size(max = 100, message = "Contract Number must have a maximum of 100 characters")
        String contractNumber,

        LocalDate contractEstablished,
        LocalDate contractStart,
        LocalDate contractEnd
) {
}

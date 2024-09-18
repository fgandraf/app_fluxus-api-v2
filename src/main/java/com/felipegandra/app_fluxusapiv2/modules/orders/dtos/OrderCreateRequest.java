package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record OrderCreateRequest(

        @NotNull(message = "Reference Code is required.")
        @Size(min = 27, max = 27, message = "Reference Code must be 27 characters long")
        String referenceCode,

        @NotNull(message = "Branch Id is required.")
        @Size(min = 4, max = 4, message = "Name must be 4 characters long")
        String branchId,

        @NotNull(message = "Order Date is required.")
        LocalDate orderDate,

        @NotNull(message = "Deadline date is required.")
        LocalDate deadline,

        @NotNull(message = "Professional Id is required.")
        @Size(min = 4, max = 4, message = "Profesional Id must be 4 characters long")
        Long professionalId,

        @NotNull(message = "Service Id is required.")
        @Size(min = 4, max = 4, message = "Service id must be 4 characters long")
        Long serviceId,

        @NotNull(message = "Service Amount is required.")
        Double serviceAmount,

        @NotNull(message = "Mileage Allowance is required.")
        Double mileageAllowance,

        @NotNull(message = "Siopi is required.")
        Boolean siopi,

        @Size(max = 100, message = "Customer name must be a maximum of 100 characters")
        String customerName,

        @Size(max = 100, message = "City must be a maximum of 100 characters")
        String city,

        @Size(max = 100, message = "Contact name must be a maximum of 100 characters")
        String contactName,

        @Size(max = 11, message = "Contact phone must be a maximum of 11 characters")
        String contactPhone,

        @Size(max = 50, message = "Coordinates must be a maximum of 50 characters")
        String coordinates,

        @NotNull(message = "Status is required.")
        int status,

        LocalDate pendingDate,
        LocalDate surveyDate,
        LocalDate doneDate,

        @Size(max = 4000, message = "Comments must be a maximum of 4000 characters")
        String comments,

        @NotNull(message = "Invoiced is required.")
        Boolean invoiced,

        Long invoiceId
) {
}
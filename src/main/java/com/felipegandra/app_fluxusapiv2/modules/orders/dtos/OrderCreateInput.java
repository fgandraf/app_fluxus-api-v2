package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

import java.time.LocalDate;

public record OrderCreateInput(
        String referenceCode,
        String branchId,
        LocalDate orderDate,
        LocalDate deadline,
        Long professionalId,
        Long serviceId,
        Double serviceAmount,
        Double mileageAllowance,
        Boolean siopi,
        String customerName,
        String city,
        String contactName,
        String contactPhone,
        String coordinates,
        int status,
        LocalDate pendingDate,
        LocalDate surveyDate,
        LocalDate doneDate,
        String comments
) {
}
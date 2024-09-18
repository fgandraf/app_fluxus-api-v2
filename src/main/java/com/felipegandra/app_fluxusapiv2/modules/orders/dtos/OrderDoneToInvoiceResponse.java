package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

import java.time.LocalDate;

public record OrderDoneToInvoiceResponse(
        Long id,
        LocalDate orderDate,
        String referenceCode,
        String professional,
        String service,
        String city,
        String customerName,
        LocalDate surveyDate,
        LocalDate doneDate,
        Double serviceAmount,
        Double mileageAllowance
) {
}
package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

import java.time.LocalDate;

public record OrderInvoiced(
        Long id,
        LocalDate orderDate,
        String referenceCode,
        Long professionalId,
        String professional,
        String service,
        String city,
        String customerName,
        LocalDate surveyDate,
        LocalDate doneDate,
        Long invoiceId,
        Double serviceAmount,
        Double mileageAllowance
) {
}
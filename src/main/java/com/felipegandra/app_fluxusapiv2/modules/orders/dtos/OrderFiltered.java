package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

import java.time.LocalDate;

public record OrderFiltered(
        Long id,
        int status,
        String professional,
        LocalDate orderDate,
        String referenceCode,
        String service,
        String city,
        String customerName,
        LocalDate surveyDate,
        LocalDate doneDate,
        Boolean invoiced
) {
}
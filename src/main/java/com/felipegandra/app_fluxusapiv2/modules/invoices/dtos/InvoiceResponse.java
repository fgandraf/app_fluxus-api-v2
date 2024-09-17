package com.felipegandra.app_fluxusapiv2.modules.invoices.dtos;

import com.felipegandra.app_fluxusapiv2.modules.invoices.Invoice;
import java.time.LocalDateTime;

public record InvoiceResponse(

        Long id,
        String description,
        LocalDateTime issueDate,
        Double subtotalService,
        Double subtotalMileage,
        Double total
) {
        public InvoiceResponse(Invoice invoice){
                this(
                        invoice.getId(),
                        invoice.getDescription(),
                        invoice.getIssueDate(),
                        invoice.getSubtotalService(),
                        invoice.getSubtotalMileage(),
                        invoice.getTotal()
                );
        }
}

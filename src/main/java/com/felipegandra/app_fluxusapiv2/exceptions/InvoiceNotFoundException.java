package com.felipegandra.app_fluxusapiv2.exceptions;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(Long id)  {
        super("Invoice id \"" + id + "\" not found");
    }
}
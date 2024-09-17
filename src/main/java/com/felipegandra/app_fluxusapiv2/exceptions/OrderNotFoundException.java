package com.felipegandra.app_fluxusapiv2.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id)  {
        super("Order Id \"" + id + "\" not found");
    }
}
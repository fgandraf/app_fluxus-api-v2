package com.felipegandra.app_fluxusapiv2.exceptions;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(Long id)  {
        super("Service Id \"" + id + "\" not found");
    }
}
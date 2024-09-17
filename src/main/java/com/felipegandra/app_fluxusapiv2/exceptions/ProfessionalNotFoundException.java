package com.felipegandra.app_fluxusapiv2.exceptions;

public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(Long id)  {
        super("Professional Id \"" + id + "\" not found");
    }
}
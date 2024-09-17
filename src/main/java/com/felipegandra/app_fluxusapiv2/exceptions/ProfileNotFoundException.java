package com.felipegandra.app_fluxusapiv2.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long id)  {
        super("Profile Id \"" + id + "\" not found");
    }
}
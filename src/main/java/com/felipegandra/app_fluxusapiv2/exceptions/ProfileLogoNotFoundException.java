package com.felipegandra.app_fluxusapiv2.exceptions;

public class ProfileLogoNotFoundException extends RuntimeException {
    public ProfileLogoNotFoundException()  {
        super("Profile logo not found");
    }
}
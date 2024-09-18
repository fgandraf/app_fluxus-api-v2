package com.felipegandra.app_fluxusapiv2.exceptions;

public class ProfileLogoNotWrittenException extends RuntimeException {
    public ProfileLogoNotWrittenException()  {
        super("Profile logo not written");
    }
}
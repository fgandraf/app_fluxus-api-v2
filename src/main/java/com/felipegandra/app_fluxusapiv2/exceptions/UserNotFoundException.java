package com.felipegandra.app_fluxusapiv2.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id)  {
        super("User Id \"" + id + "\" not found");
    }

    public UserNotFoundException(String username)  {
        super("User \"" + username + "\" not found");
    }
}
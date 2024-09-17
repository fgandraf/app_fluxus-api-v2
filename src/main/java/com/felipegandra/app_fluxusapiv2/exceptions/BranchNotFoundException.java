package com.felipegandra.app_fluxusapiv2.exceptions;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String id)  {
        super("Branch Id \"" + id + "\" not found");
    }
}
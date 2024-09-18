package com.felipegandra.app_fluxusapiv2.exceptions;

public class NoRecordsFoundException extends RuntimeException {

    public NoRecordsFoundException() {
        super("No records found");
    }
}
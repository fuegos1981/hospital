package com.epam.hospital.repository;

public class DBException extends Exception {
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}

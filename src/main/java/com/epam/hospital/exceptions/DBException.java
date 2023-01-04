package com.epam.hospital.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBException extends Exception {
    private static Logger logger = LogManager.getLogger();
    public DBException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message);
    }
    public DBException(String message) {
        super(message);
        logger.error(message);
    }
}

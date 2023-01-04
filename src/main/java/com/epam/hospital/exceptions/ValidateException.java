package com.epam.hospital.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidateException extends Exception {
    private static Logger logger = LogManager.getLogger();
    public ValidateException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message);
    }
    public ValidateException(String message) {
        super(message);
        logger.error(message);
    }
}

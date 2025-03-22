package com.hospital.app.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidateException extends Exception {
    private final static Logger logger = LogManager.getLogger();
    public ValidateException(String message, Throwable cause) {
        super(message, cause);
        logger.error("not correct: "+message);
    }
    public ValidateException(String message) {
        super(message);
        logger.error("not correct: "+message);
    }
}

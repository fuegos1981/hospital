package com.epam.hospital;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccessException extends Exception{
    private static Logger logger = LogManager.getLogger();
    public AccessException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message);
    }
    public AccessException(String message) {
        super(message);
        logger.error(message);
    }
}

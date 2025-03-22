package com.hospital.app.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccessException extends Exception{
    private static Logger logger = LogManager.getLogger();

    public AccessException(String message) {
        super(message);
        logger.error("not rights: "+message);
    }
}

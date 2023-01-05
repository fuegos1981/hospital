package com.epam.hospital.controller.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionListener implements HttpSessionAttributeListener {
    private static Logger logger = LogManager.getLogger();
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equals("user_name")||event.getName().equals("role")) {
            logger.info("add: " + event.getClass().getSimpleName() + " : " + event.getName()
                    + " : " + event.getValue());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals("user_name")||event.getName().equals("role")) {
            logger.info("add: " + event.getClass().getSimpleName() + " : " + event.getName()
                    + " : " + event.getValue());
        }
    }
}

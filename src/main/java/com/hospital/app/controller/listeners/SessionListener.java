package com.hospital.app.controller.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * An implementation of the Filter Listener to fix the start and end time of the user's work in the program.
 *Please see the {@link javax.servlet.annotation.WebListener}  for true identity
 * @author Sinkevych Olena
 *
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener {
    private final static Logger logger = LogManager.getLogger();
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equals("user_name")||event.getName().equals("role")) {
            logger.info(event.getSession().getId()+"; come: " + event.getName()
                    + " : " + event.getValue());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals("user_name")||event.getName().equals("role")) {
            logger.info(event.getSession().getId()+"; exit: " + event.getName()
                    + " : " + event.getValue());
        }
    }
}

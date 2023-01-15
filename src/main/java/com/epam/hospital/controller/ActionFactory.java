package com.epam.hospital.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is an element of the implementation of the Factory Method template,
 *used to determine the type and create a command instance.
 * @author Sinkevych Olena
 *
 */
public class ActionFactory {
    /**
     * <p>This method extracts the value of the command parameter from the request and, based on it, extracts
     * command object corresponding to the request.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @return  command object corresponding to the request
     *
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        return action==null?null:CommandEnum.valueOf(action.toUpperCase()).getCurrentCommand();
    }
}

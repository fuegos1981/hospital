package com.epam.hospital.controller;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
        return currentEnum.getCurrentCommand();

    }
}

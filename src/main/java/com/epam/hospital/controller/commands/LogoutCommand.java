package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        request.getSession().invalidate();
        return "index.jsp";
    }
}

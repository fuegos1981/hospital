package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.User;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
    private final static Service<User> userService;
    private static final String PARAM_NAME_LOGIN = "username";
    private static final String PARAM_NAME_PASSWORD = "password";

    static {
        userService = UserService.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        try {

            User user = ((UserService) userService).readByName(login);
            request.setAttribute(PARAM_NAME_LOGIN, login);
            if (user == null || !user.getPassword().equals(pass)) {
                if (request.getParameter("submit_us")==null&request.getParameter("submit_ua")==null) {
                    request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("error_log_pass"));
                }
                return ControllerConstants.PAGE_INDEX;
            } else {
                request.removeAttribute(ControllerConstants.MESSAGE);
                HttpSession session = request.getSession(true);
                session.setAttribute(ControllerConstants.ROLE, user.getRole());
                session.setAttribute("login", login);
                return new AdminCommand().execute(request, currentMessageLocale);
            }
        } catch (DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

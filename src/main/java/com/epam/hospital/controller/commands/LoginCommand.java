package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
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
            User user = ((UserService)userService).readByName(login);
            request.setAttribute("username", login);
            request.setAttribute("password", pass);
            if (user== null||!user.getPassword().equals(pass)){
                request.setAttribute("error", currentMessageLocale.getString("error_log_pass"));
                return "/index.jsp";
            }
            else{
                request.removeAttribute("error");
                request.removeAttribute("password");
                HttpSession session = request.getSession(true);
                session.setAttribute("role",user.getRole());
                session.setAttribute("login",login);
                return new AdminCommand().execute(request, currentMessageLocale);
            }
        } catch (DBException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";
        }
    }
}

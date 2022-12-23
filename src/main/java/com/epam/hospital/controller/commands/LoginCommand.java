package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "username";
    private static final String PARAM_NAME_PASSWORD = "password";
    private final Service<Doctor> doctorService = DoctorService.getDoctorService();


    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {

        String login = request.getParameter(PARAM_NAME_LOGIN);
        request.setAttribute(PARAM_NAME_LOGIN, login);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            Doctor user = ((DoctorService) doctorService).readByLogin(login);
            if (request.getParameter(ControllerConstants.SUBMIT)==null){
                return ControllerConstants.PAGE_INDEX;
            }

            else if (user == null || !user.getPassword().equals(pass)) {
                    request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("error_log_pass"));
                    return ControllerConstants.PAGE_INDEX;
            }
            else {
                HttpSession session = request.getSession(true);
                session.setAttribute(ControllerConstants.ROLE, user.getRole());
                return new AdminCommand().execute(request, currentMessageLocale);
            }
        } catch (DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

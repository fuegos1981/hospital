package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Role;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.ValidateException;

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
            if (request.getParameter(ControllerConstants.SUBMIT)==null){
                return ControllerConstants.PAGE_INDEX;
            }
            Doctor user = ((DoctorService) doctorService).readByLoginPassword(login, pass);
            HttpSession session = request.getSession(true);
            session.setAttribute(ControllerConstants.ROLE, user.getRole());
            session.setAttribute(PARAM_NAME_LOGIN, user.getLogin());
            session.setAttribute("user_id", user.getId());
                if (user.getRole()== Role.ADMIN)
                    return "/hospital/admin?command=admin";
                   // return new AdminCommand().execute(request, currentMessageLocale);
                else
                    return "/hospital/medic?command=medic";//new MedicCommand().execute(request, currentMessageLocale);

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("error_log_pass"));
            return ControllerConstants.PAGE_INDEX;
        } catch (DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

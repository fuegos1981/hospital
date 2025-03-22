package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Role;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.DoctorService;
import com.hospital.app.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The class implements a command for login user
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "username";
    private static final String PARAM_NAME_PASSWORD = "password";
    private Service<Doctor> doctorService = DoctorService.getDoctorService();

    /**
     * <p>This method generates a page or path with a response to the client when login user.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost...).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when login user.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException {

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        ControllerUtils.setAttributes(request,PARAM_NAME_LOGIN, PARAM_NAME_PASSWORD);
        try {
            if (request.getParameter(ControllerConstants.SUBMIT)==null){
                return ControllerConstants.PAGE_INDEX;
            }
            Doctor user = ((DoctorService) doctorService).readByLoginPassword(login, pass);
            HttpSession session = request.getSession(true);
            session.setAttribute(ControllerConstants.ROLE, user.getRole());
            session.setAttribute("user_name", user.getLastName()+" "+user.getFirstName());
            session.setAttribute("user_id", user.getId());
                if (user.getRole()== Role.ADMIN)
                    return "/hospital/admin?command=admin";
                else
                    return "/hospital/medic?command=medic&doctor_id="+user.getId();

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("error_log_pass"));
            return ControllerConstants.PAGE_INDEX;
        }
    }
}

package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.service.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * The class implements a command for logout user
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class LogoutCommand implements ActionCommand {

    /**
     * <p>This method generates a page or path with a response to the client when logout user.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost...).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when logout user.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        request.getSession().invalidate();
        return "/index.jsp";
    }
}

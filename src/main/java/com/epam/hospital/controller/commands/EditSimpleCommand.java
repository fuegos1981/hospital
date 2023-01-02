package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.RepositoryUtils;
import com.epam.hospital.service.impl.SimpleService;
import com.epam.hospital.service.impl.ValidateException;
import javax.servlet.http.HttpServletRequest;

/**
 * The class implements a command for creating a simple command (category, diagnosis, etc)
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * Please see the {@link com.epam.hospital.model.SimpleModel}  for true identity
 * @author Sinkevych Olena
 *
 */
public class EditSimpleCommand implements ActionCommand {

    /**
     * <p>This method generates a page or path with a response to the client when creating a simple model (category, diagnosis, etc).
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when creating a simple model (category, diagnosis, etc).
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException {
        ControllerUtils.setAttributes(request,ControllerConstants.SIMPLE,ControllerConstants.NAME);
        String name_Class = request.getParameter(ControllerConstants.NAME);
        String simple =request.getParameter(ControllerConstants.SIMPLE);
        try {
            if (request.getParameter(ControllerConstants.SUBMIT) == null ){
                    return ControllerConstants.PAGE_EDIT_SIMPLE;
            }
            else {
                SimpleService simpleService= SimpleService.getSimpleService(name_Class);
                SimpleModel simpleModel = RepositoryUtils.getSimpleInstance(name_Class);
                simpleModel.setName(simple);
                simpleService.create(simpleModel);
                if (name_Class.equalsIgnoreCase("Category")) {
                    return "/hospital/admin?command=admin";
                }
                else
                    return "/hospital/editAppointment?command=edit_appointment";
            }

        } catch (ValidateException e) {
            request.setAttribute(currentMessageLocale.getString("not_correct")+" "+ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_EDIT_SIMPLE;

        }
    }
}

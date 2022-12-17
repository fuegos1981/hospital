package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Category;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.SimpleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;

public class EditSimpleCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        SimpleService simpleService= SimpleService.getSimpleService(ControllerConstants.NAME);
        String name_Class = request.getParameter(ControllerConstants.NAME);
        request.setAttribute(ControllerConstants.NAME,name_Class);
        try {
            if (name_Class==null||request.getParameter("simple")==null){
                return ControllerConstants.PAGE_EDIT_SIMPLE;
            }
            if (name_Class.equalsIgnoreCase("Category")){
                simpleService.create(Category.createInstance(request.getParameter("simple")));
                return new CreateDoctorCommand().execute(request,currentMessageLocale);//.ControllerConstants.PAGE_EDIT_DOCTOR;
            }
            return ControllerConstants.PAGE_EDIT_DOCTOR;
/*
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_EDIT_SIMPLE;
*/

        } catch (DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

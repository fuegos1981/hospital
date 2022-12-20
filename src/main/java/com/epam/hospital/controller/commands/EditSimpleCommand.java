package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Category;
import com.epam.hospital.model.SimpleModel;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.RepositoryUtils;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.SimpleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;

public class EditSimpleCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        String name_Class = request.getParameter(ControllerConstants.NAME);
        request.setAttribute(ControllerConstants.NAME,name_Class);
        String simple =request.getParameter(ControllerConstants.SIMPLE);
        request.setAttribute(ControllerConstants.SIMPLE,simple);
        try {
            if (request.getParameter(ControllerConstants.SUBMIT) == null ){
                    return ControllerConstants.PAGE_EDIT_SIMPLE;
            }
            else {
                SimpleService simpleService= SimpleService.getSimpleService(name_Class);
                SimpleModel simpleModel = RepositoryUtils.getSimpleInstance(name_Class);
                simpleModel.setName(simple);
                simpleService.create(simpleModel);
                ControllerUtils.RemoveAttributes(request, ControllerConstants.SIMPLE, ControllerConstants.NAME);
                //if (name_Class.equalsIgnoreCase("Category")) {
                //    simpleService.create(Category.createInstance(request.getParameter("simple")));
                 //   return new CreateDoctorCommand().execute(request, currentMessageLocale);//.ControllerConstants.PAGE_EDIT_DOCTOR;
                //}
                return new CreateDoctorCommand().execute(request, currentMessageLocale);//ControllerConstants.PAGE_EDIT_DOCTOR;//
            }

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_EDIT_SIMPLE;

        } catch (DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            ControllerUtils.RemoveAttributes(request, ControllerConstants.SIMPLE, ControllerConstants.NAME);
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

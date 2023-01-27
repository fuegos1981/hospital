package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.impl.SimpleService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DeleteSimpleCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale){
        try {
            String name_Class = request.getParameter(ControllerConstants.NAME);
            SimpleService simpleService= SimpleService.getSimpleService(name_Class);
            simpleService.delete(simpleService.readById(ControllerUtils.parseID(request, Fields.ID)));
            return "/hospital/Simple?command=simple";
        } catch (ValidateException | DBException | SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

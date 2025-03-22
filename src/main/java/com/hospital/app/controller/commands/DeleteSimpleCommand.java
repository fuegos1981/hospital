package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.impl.SimpleService;

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

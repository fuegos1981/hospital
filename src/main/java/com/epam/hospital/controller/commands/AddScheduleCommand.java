package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class AddScheduleCommand implements ActionCommand {
    private final static Service<Doctor> doctorService = DoctorService.getDoctorService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        int id = Integer.parseInt(request.getParameter(ControllerConstants.ID));
        try {
            request.setAttribute(ControllerConstants.Name, request.getParameter(ControllerConstants.Name).replace(ControllerConstants.PERC," "));
            request.setAttribute(ControllerConstants.DOCTORS,doctorService.getAll(null));
            return ControllerConstants.PAGE_ADD_SCHEDULE;
        } catch (SQLException|DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

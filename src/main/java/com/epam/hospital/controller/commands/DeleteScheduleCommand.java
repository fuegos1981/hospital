package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DeleteScheduleCommand implements ActionCommand {
    private final Service<Schedule> scheduleService = ScheduleService.getScheduleService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {
        try {
            scheduleService.delete(scheduleService.readById( ControllerUtils.parseID(request, Fields.ID)));
            return "/hospital/readPatient?id="+ControllerUtils.parseID(request, Fields.PATIENT_ID)+"&command=patient_info";
        } catch (ValidateException|DBException|SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

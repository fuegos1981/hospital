package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.dto.ScheduleDto;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * The class implements a command for deleting a schedule
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class DeleteScheduleCommand implements ActionCommand {
    private final Service<ScheduleDto> scheduleService = ScheduleService.getScheduleService();

    /**
     * <p>This method generates a page or path with a response to the client when deleting a schedule.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when deleting a schedule.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale){
        try {
            ControllerUtils.setPathReturn(request);
            scheduleService.delete(scheduleService.readById( ControllerUtils.parseID(request, Fields.ID)));
            return (String) request.getAttribute("path_return");
        } catch (ValidateException|DBException|SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

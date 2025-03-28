package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.dto.ScheduleDto;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.ScheduleService;
import com.hospital.app.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * The class implements a command for deleting a schedule
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class DeleteScheduleCommand implements ActionCommand {
    private final Service<ScheduleDto> scheduleService = ScheduleService.getScheduleService();

    /**
     * <p>This method generates a page or path with a response to the client when deleting a schedule.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost...).
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
        } catch (ValidateException |DBException|SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

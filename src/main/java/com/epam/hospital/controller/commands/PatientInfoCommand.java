package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.dto.AppointmentDto;
import com.epam.hospital.dto.ScheduleDto;
import com.epam.hospital.model.Patient;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.AppointmentService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class implements working with patient information
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class PatientInfoCommand implements ActionCommand {
    private final Service<Patient> patientService= PatientService.getPatientService();
    private final Service<ScheduleDto> scheduleService= ScheduleService.getScheduleService();
    private final Service<AppointmentDto> appointmentService= AppointmentService.getAppointmentService();
    private final static String CURRENT_PAGE_SCHEDULE = "current_page_schedule";
    private final static String COUNT_PAGE_SCHEDULE = "count_page_schedule";
    private final static String CURRENT_PAGE_APPOINTMENT = "current_page_appointment";
    private final static String COUNT_PAGE_APPOINTMENT = "count_page_appointment";
    /**
     * <p>This method generates a page or path with a response to the client when working with patient information.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {
        try {
            int id = Integer.parseInt(request.getParameter(Fields.ID));
            request.setAttribute(Fields.PATIENT_ID,id);
            request.setAttribute("patient",patientService.readById(id));
            Map<String,Object> selection = new HashMap<>();
            selection.put(Fields.PATIENT_ID,id);
            int[] limitSchedule = ControllerUtils.setMasForPagination(request, scheduleService.getSize(selection),
                    CURRENT_PAGE_SCHEDULE,COUNT_PAGE_SCHEDULE);
            request.setAttribute("schedules",scheduleService.getAll(selection, null, limitSchedule));
            int[] limitApp = ControllerUtils.setMasForPagination(request, appointmentService.getSize(selection),
                    CURRENT_PAGE_APPOINTMENT,COUNT_PAGE_APPOINTMENT);
            request.setAttribute("appointments",appointmentService.getAll(selection, null, limitApp));
            return ControllerConstants.PAGE_PATIENT_INFO;
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_ERROR;
        }

    }
}

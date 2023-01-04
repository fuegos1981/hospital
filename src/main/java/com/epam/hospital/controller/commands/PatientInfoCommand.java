package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.exceptions.DBException;
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
    private final Service<Schedule> scheduleService= ScheduleService.getScheduleService();
    private final Service<Appointment> appointmentService= AppointmentService.getAppointmentService();

    /**
     * <p>This method generates a page or path with a response to the client when working with patient information.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("patient_id",id);
            request.setAttribute("patient",patientService.readById(id));
            Map<String,Integer> selection = new HashMap<>();
            selection.put("patient_id",id);
            request.setAttribute("schedules", scheduleService.getAll(selection, null, null));
            request.setAttribute("appointments",appointmentService.getAll(selection, null,null));
            return ControllerConstants.PAGE_PATIENT_INFO;
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_ERROR;
        }

    }
}

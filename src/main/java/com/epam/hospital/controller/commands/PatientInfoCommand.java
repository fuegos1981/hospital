package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.AppointmentService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PatientInfoCommand implements ActionCommand {
    private Service<Patient> patientService= PatientService.getPatientService();
    private Service<Schedule> scheduleService= ScheduleService.getScheduleService();
    private Service<Appointment> appointmentService= AppointmentService.getAppointmentService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("patient_id",id);
            request.setAttribute("patient",patientService.readById(id));
            Map<String,Integer> selection = new HashMap<>();
            selection.put("patient_id",id);
            request.setAttribute("schedules",((ScheduleService) scheduleService).getAll(selection));
            request.setAttribute("appointments",((AppointmentService)appointmentService).getAll(selection));
            return ControllerConstants.PAGE_PATIENT_INFO;
        } catch (DBException | SQLException e) {
            request.setAttribute("message", e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_ERROR;
        }

    }
}
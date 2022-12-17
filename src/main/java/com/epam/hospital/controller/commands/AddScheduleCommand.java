package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class AddScheduleCommand implements ActionCommand {
    private final static Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final static Service<Patient> patientService = PatientService.getPatientService();
    private final static Service<Schedule> scheduleService = ScheduleService.getScheduleService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        int id = Integer.parseInt(request.getParameter(ControllerConstants.ID));
        boolean isPatient = Boolean.parseBoolean(request.getParameter("is_patient"));
        ControllerUtils.setNameFromParameter(request);
        ControllerUtils.setAttributes(request, "id", "is_patient",ControllerConstants.NAME);
        try {

            Date dateVisit = ControllerUtils.getDateByString(request.getParameter("visit_time"), true);


            String message = "";
            Integer doctorId = request.getParameter("doctor_id")==null?null:Integer.valueOf(request.getParameter("doctor_id"));
            Integer patientId =request.getParameter("patient_id")==null?null:Integer.valueOf(request.getParameter("patient_id"));
            boolean isError = false;


            if (request.getParameter(ControllerConstants.SUBMIT) == null && isPatient) {
                request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll(null));
                return ControllerConstants.PAGE_ADD_SCHEDULE;
            } else if (request.getParameter(ControllerConstants.SUBMIT) == null && !isPatient) {
                request.setAttribute(ControllerConstants.PATIENTS, patientService.getAll(null));
                return ControllerConstants.PAGE_ADD_SCHEDULE;
            } else if (isPatient && doctorId == null) {
                message = "doctor";
                isError = true;
            } else if (!isPatient && patientId == null) {
                message = "incorrect patient";
                isError = true;
            }
            if (dateVisit == null) {
                message = "visit time";
                isError = true;
            }
            if (isError) {
                throw new ValidateException(currentMessageLocale.getString("error_log_pass"));
            } else {
                Patient patient = patientService.readById((isPatient) ? id : patientId);
                Doctor doctor = doctorService.readById((isPatient) ? doctorId : id);
                scheduleService.create(Schedule.createSchedule(doctor, patient, dateVisit));
                return new AdminCommand().execute(request, currentMessageLocale);
            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ADD_SCHEDULE;
        } catch (SQLException | DBException | ParseException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }


}


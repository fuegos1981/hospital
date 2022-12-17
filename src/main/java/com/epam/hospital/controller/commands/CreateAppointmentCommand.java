package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.*;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;


public class CreateAppointmentCommand implements ActionCommand {
    private final static Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final static Service<Patient> patientService = PatientService.getPatientService();
    private final static SimpleService diagnosisService = SimpleService.getSimpleService(Constants.DIAGNOSIS);
    private final static Service<Appointment> appointmentService = AppointmentService.getAppointmentService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        int id = Integer.parseInt(request.getParameter(ControllerConstants.PATIENT_ID));
        ControllerUtils.setNameFromParameter(request);
        Integer doctorId = request.getParameter(ControllerConstants.DOCTOR_ID)==null?null:Integer.valueOf(request.getParameter(ControllerConstants.DOCTOR_ID));
        Integer diagnosisId = request.getParameter(ControllerConstants.DIAGNOSIS_ID)==null?null:Integer.valueOf(request.getParameter(ControllerConstants.DIAGNOSIS_ID));
        ControllerUtils.setAttributes(request,ControllerConstants.PATIENT_ID);
        try {
            //String message = "";
            //boolean isError = false;

            if (request.getParameter(ControllerConstants.SUBMIT) == null ) {
                request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll(null));
                request.setAttribute(ControllerConstants.DIAGNOSISES, diagnosisService.getAll(null));
                return ControllerConstants.PAGE_EDIT_APPOINTMENT;
            }
            else{
                Doctor doctor = doctorService.readById(doctorId );
                appointmentService.create(Appointment.createAppointment(new Date(),
                        (Diagnosis) diagnosisService.readById(diagnosisId),
                        patientService.readById(id),
                        doctor));
                return ControllerConstants.PAGE_PATIENT_INFO;
            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ADD_SCHEDULE;
        } catch (SQLException | DBException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

}

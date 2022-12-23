package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.*;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;



public class CreateAppointmentCommand implements ActionCommand {
    private final Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final Service<Patient> patientService = PatientService.getPatientService();
    private final SimpleService diagnosisService = SimpleService.getSimpleService(Constants.DIAGNOSIS);
    private final Service<Appointment> appointmentService = AppointmentService.getAppointmentService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        Integer id = ControllerUtils.parseID(request,Fields.ID);
        ControllerUtils.setNameFromParameter(request);
        request.setAttribute("title",id==null?"create_appointment":"edit_appointment");
        ControllerUtils.setAttributes(request,ControllerConstants.ID,ControllerConstants.NAME);
        try {
            request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll(null,null));
            request.setAttribute(ControllerConstants.DIAGNOSISES, diagnosisService.getAll(null,null));
            Appointment appointment= getAppointment(request,id);
            if (request.getParameter(ControllerConstants.SUBMIT) == null ) {
                return ControllerConstants.PAGE_EDIT_APPOINTMENT;
            }
            else{
                appointmentService.create(appointment);
                return "/hospital/readPatient?id="+appointment.getPatient().getId()+"&command=patient_info";
            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_EDIT_APPOINTMENT;
        } catch (SQLException | DBException|ParseException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

    public Appointment getAppointment(HttpServletRequest request, Integer id) throws ParseException, DBException, ValidateException, SQLException {
        Appointment appointment;

        if (id!=null&&request.getParameter("not_first")==null){
            appointment = appointmentService.readById(id);
            request.setAttribute("not_first", "");
        }
        else {
            Integer diagnosisId= ControllerUtils.parseID(request,ControllerConstants.DIAGNOSIS_ID);
            Integer patientId= ControllerUtils.parseID(request,ControllerConstants.PATIENT_ID);
            Integer doctorId= ControllerUtils.parseID(request,ControllerConstants.DOCTOR_ID);
            appointment = Appointment.createAppointment(new Date(),
                    diagnosisId==null?null:(Diagnosis) diagnosisService.readById(diagnosisId),
                    patientId==null?null: patientService.readById(patientId),
                    doctorId==null?null: doctorService.readById(doctorId));

        }
        request.setAttribute("appointment", appointment);

        return appointment;

    }


}

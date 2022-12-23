package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
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
    private final Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final Service<Patient> patientService = PatientService.getPatientService();
    private final Service<Schedule> scheduleService = ScheduleService.getScheduleService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        Integer id = ControllerUtils.parseID(request,Fields.ID);
        Integer doctorId = ControllerUtils.parseID(request,Fields.DOCTOR_ID);
        Integer patientId =ControllerUtils.parseID(request,Fields.PATIENT_ID);
        boolean isPatient = Boolean.parseBoolean(request.getParameter("is_patient"));
        ControllerUtils.setNameFromParameter(request);
        ControllerUtils.setAttributes(request, Fields.ID,Fields.DOCTOR_ID,Fields.PATIENT_ID,Fields.VISIT_TIME,
                "is_patient",ControllerConstants.NAME);
        try {
            if (isPatient) {
                request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll(null,null));
            } else {
                request.setAttribute(ControllerConstants.PATIENTS, patientService.getAll(null, null));
            }
            Date dateVisit = ControllerUtils.getDateByString(request.getParameter(Fields.VISIT_TIME), true);

            if (request.getParameter(ControllerConstants.SUBMIT)==null) {
                return ControllerConstants.PAGE_ADD_SCHEDULE;
            } else {
                Patient patient = patientService.readById(patientId);
                Doctor doctor = doctorService.readById(doctorId);
                Schedule schedule = Schedule.createSchedule(doctor, patient, dateVisit);
                if (id==null){
                    scheduleService.create(schedule);
                }
                else{
                    schedule.setId(id);
                    scheduleService.update(schedule);
                }
                //return new AdminCommand().execute(request, currentMessageLocale);
                return "/hospital/readPatient?id="+patient.getId()+"&command=patient_info";
            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_ADD_SCHEDULE;
        } catch (SQLException | DBException | ParseException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }


}


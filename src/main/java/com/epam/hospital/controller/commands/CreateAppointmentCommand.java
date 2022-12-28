package com.epam.hospital.controller.commands;

import com.epam.hospital.AccessException;
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
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;


public class CreateAppointmentCommand implements ActionCommand {
    private final Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final Service<Patient> patientService = PatientService.getPatientService();
    private final SimpleService diagnosisService = SimpleService.getSimpleService(Constants.DIAGNOSIS);
    private final Service<Appointment> appointmentService = AppointmentService.getAppointmentService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        Integer id = ControllerUtils.parseID(request,Fields.ID);
        ControllerUtils.setNameFromParameter(request);
        //request.setAttribute("title",id==null?"create_appointment":"edit_appointment");
        ControllerUtils.setAttributes(request,ControllerConstants.ID,ControllerConstants.NAME, ControllerConstants.PATIENT_ID);
        try {
            request.setAttribute(ControllerConstants.PATIENTS, patientService.getAll(null, null));
            request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll(null,null));
            request.setAttribute(ControllerConstants.DIAGNOSISES, diagnosisService.getAll(null,null));
            Appointment appointment= getAppointment(request,id);
            if (request.getParameter(ControllerConstants.SUBMIT) == null ) {
                return ControllerConstants.PAGE_EDIT_APPOINTMENT;
            }
            else{
                checkAccess(request,appointment);
                if (id==null){
                    appointmentService.create(appointment);
                }
                else{
                    appointment.setId(id);
                    appointmentService.update(appointment);
                }
                    return "/hospital/readPatient?id="+appointment.getPatient().getId()+"&command=patient_info";

            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_APPOINTMENT;
        }
        catch (AccessException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_rights")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_APPOINTMENT;
        }catch (SQLException | DBException|ParseException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

    private void checkAccess(HttpServletRequest request,Appointment appointment) throws AccessException {
        HttpSession session= request.getSession();
        Role role = (Role) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("user_id");
        if (role!=Role.ADMIN && !userId.equals(appointment.getDoctor().getId())){
            throw new AccessException("other_doctor");
        }
        if (role==Role.NURSE && !appointment.getOperation().isEmpty()){
            throw new AccessException("operation_mast_be_empty");
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
            ControllerUtils.setAttributes(request,ControllerConstants.DIAGNOSIS_ID,ControllerConstants.PATIENT_ID,ControllerConstants.DOCTOR_ID);
            appointment = Appointment.createAppointment(new Date(),
                    diagnosisId==null?null:(Diagnosis) diagnosisService.readById(diagnosisId),
                    patientId==null?null: patientService.readById(patientId),
                    doctorId==null?null: doctorService.readById(doctorId));
            appointment.setMedication(Optional.ofNullable(request.getParameter(Fields.MEDICATION)).orElse(""));
            appointment.setProcedure(Optional.ofNullable(request.getParameter(Fields.PROCEDURE)).orElse(""));
            appointment.setOperation(Optional.ofNullable(request.getParameter(Fields.OPERATION)).orElse(""));
        }
        request.setAttribute("appointment", appointment);
        return appointment;
    }


}

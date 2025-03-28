package com.hospital.app.controller.commands;

import com.hospital.app.dto.AppointmentDto;
import com.hospital.app.exceptions.AccessException;
import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Patient;
import com.hospital.app.model.Role;
import com.hospital.app.repository.Constants;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.AppointmentService;
import com.hospital.app.service.impl.DoctorService;
import com.hospital.app.service.impl.PatientService;
import com.hospital.app.service.impl.SimpleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;


/**
 * The class implements a command for creating and editing an appointment
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class CreateAppointmentCommand implements ActionCommand {
    private final Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final Service<Patient> patientService = PatientService.getPatientService();
    private final SimpleService diagnosisService = SimpleService.getSimpleService(Constants.DIAGNOSIS);
    private final Service<AppointmentDto> appointmentService = AppointmentService.getAppointmentService();

    /**
     * <p>This method generates a page or path with a response to the client when creating or editing an appointment.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when creating or editing an appointment.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException {
        ControllerUtils.setPathReturn(request);
        Integer id = ControllerUtils.parseID(request,Fields.ID);
        ControllerUtils.setAttributes(request,ControllerConstants.ID, ControllerConstants.PATIENT_ID);
        try {
            request.setAttribute(ControllerConstants.PATIENTS, patientService.getAll());
            request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll());
            request.setAttribute(ControllerConstants.DIAGNOSISES, diagnosisService.getAll());
            AppointmentDto appointment= getAppointment(request,id);
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
                return request.getParameter("path_return");

            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct")+": "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_APPOINTMENT;
        }
        catch (AccessException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_rights")+": "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_APPOINTMENT;
        }
    }

    private void checkAccess(HttpServletRequest request,AppointmentDto appointment) throws AccessException {
        HttpSession session= request.getSession();
        Role role = (Role) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("user_id");
        if (role!=Role.ADMIN && !userId.equals(appointment.getDoctorId())){
            throw new AccessException("other_doctor");
        }
        if (role==Role.NURSE && !appointment.getOperation().isEmpty()){
            throw new AccessException("operation_mast_be_empty");
        }
    }

    private AppointmentDto getAppointment(HttpServletRequest request, Integer id) throws DBException, ValidateException, SQLException, ParseException {

        AppointmentDto appointment;
        if (id!=null&&request.getParameter("isFirst")!=null){
            appointment = appointmentService.readById(id);
            request.setAttribute(Fields.PATIENT_ID,appointment.getPatientId());
            request.setAttribute(Fields.DOCTOR_ID,appointment.getDoctorId());
            request.setAttribute(Fields.DATE_CREATE, appointment.getDateCreate().toString());
        }
        else {
            ControllerUtils.setAttributes(request, Fields.DATE_CREATE);
            Date dateCreate = ControllerUtils.getDateByString(request.getParameter(Fields.DATE_CREATE),false);
            Integer diagnosisId= ControllerUtils.parseID(request,ControllerConstants.DIAGNOSIS_ID);
            Integer patientId= ControllerUtils.parseID(request,ControllerConstants.PATIENT_ID);
            Integer doctorId= ControllerUtils.parseID(request,ControllerConstants.DOCTOR_ID);
            appointment = AppointmentDto.createAppointmentDto(dateCreate==null?new Date():dateCreate, diagnosisId,patientId,doctorId);
            appointment.setMedication(Optional.ofNullable(request.getParameter(Fields.MEDICATION)).orElse(""));
            appointment.setProcedure(Optional.ofNullable(request.getParameter(Fields.PROCEDURE)).orElse(""));
            appointment.setOperation(Optional.ofNullable(request.getParameter(Fields.OPERATION)).orElse(""));
        }

        request.setAttribute("appointment", appointment);
        return appointment;
    }


}

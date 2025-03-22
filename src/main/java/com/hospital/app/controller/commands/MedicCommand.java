package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.dto.AppointmentDto;
import com.hospital.app.dto.ScheduleDto;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Patient;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.SortRule;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.AppointmentService;
import com.hospital.app.service.impl.DoctorService;
import com.hospital.app.service.impl.PatientService;
import com.hospital.app.service.impl.ScheduleService;
import com.hospital.app.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class implements working in medic interface
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class MedicCommand implements ActionCommand {
    private final Service<Patient> patientService= PatientService.getPatientService();
    private final Service<Doctor> doctorService= DoctorService.getDoctorService();
    private final Service<ScheduleDto> scheduleService= ScheduleService.getScheduleService();
    private final Service<AppointmentDto> appointmentService= AppointmentService.getAppointmentService();
    private final static String CURRENT_PAGE_SCHEDULE = "current_page_schedule";
    private final static String COUNT_PAGE_SCHEDULE = "count_page_schedule";
    private final static String CURRENT_PAGE_APPOINTMENT = "current_page_appointment";
    private final static String COUNT_PAGE_APPOINTMENT = "count_page_appointment";

    /**
     * <p>This method generates a page or path with a response to the client when the doctor or nurse are working in the main interface.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {

        ControllerUtils.setAttributes(request,ControllerConstants.PATIENT_ID,ControllerConstants.DOCTOR_ID);
        try {
            Map<String,Object> selection = getSelection(request);
            request.setAttribute("patients",patientService.getAll(QueryRedactor.getRedactor(SortRule.NAME_ASC)));
            request.setAttribute("doctors",doctorService.getAll(QueryRedactor.getRedactor(SortRule.NAME_ASC)));
            int[] limitSchedule = ControllerUtils.setMasForPagination(request, scheduleService.getSize(QueryRedactor.getRedactor(selection)),
                    CURRENT_PAGE_SCHEDULE,COUNT_PAGE_SCHEDULE);
            request.setAttribute("schedules",scheduleService.getAll(
                    QueryRedactor.getRedactor(selection, SortRule.VISIT_TIME_DESC, limitSchedule)));
            int[] limitApp = ControllerUtils.setMasForPagination(request, appointmentService.getSize(QueryRedactor.getRedactor(selection)),
                    CURRENT_PAGE_APPOINTMENT,COUNT_PAGE_APPOINTMENT);
            request.setAttribute("appointments",appointmentService.getAll(
                    QueryRedactor.getRedactor(selection, SortRule.DATE_CREATE_DESC, limitApp)));
            return ControllerConstants.PAGE_MEDIC;
        }catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_PATIENT;
        }
    }

    private Map<String,Object> getSelection(HttpServletRequest request) throws DBException, ValidateException, SQLException {
        Map<String,Object> selection = new HashMap<>();
        Integer patientId = ControllerUtils.parseID(request,ControllerConstants.PATIENT_ID);
        Integer doctorId;
        doctorId  = ControllerUtils.parseID(request,ControllerConstants.DOCTOR_ID);
        if (patientId!=null) {
            request.setAttribute("patient", patientService.readById(patientId));
            selection.put(ControllerConstants.PATIENT_ID,patientId);
        }if (doctorId!=null) {
            request.setAttribute("doctor", doctorService.readById(doctorId));
            selection.put(ControllerConstants.DOCTOR_ID,doctorId);
        }
        return selection;
    }
}

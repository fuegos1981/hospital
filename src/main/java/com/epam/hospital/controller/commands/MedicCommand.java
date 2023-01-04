package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Schedule;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.AppointmentService;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The class implements working in medic interface
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class MedicCommand implements ActionCommand {
    private final Service<Patient> patientService= PatientService.getPatientService();
    private final Service<Doctor> doctorService= DoctorService.getDoctorService();
    private final Service<Schedule> scheduleService= ScheduleService.getScheduleService();
    private final Service<Appointment> appointmentService= AppointmentService.getAppointmentService();
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
            Map<String,Integer> selection = getSelection(request);
            request.setAttribute("patients",patientService.getAll(null, SortRule.NAME_ASC, null));
            request.setAttribute("doctors",doctorService.getAll(null, SortRule.NAME_ASC, null));
            int[] limitSchedule = ControllerUtils.setMasForPagination(request, scheduleService.getSize(selection),
                    CURRENT_PAGE_SCHEDULE,COUNT_PAGE_SCHEDULE);
            request.setAttribute("schedules",scheduleService.getAll(selection, null, limitSchedule));
            int[] limitApp = ControllerUtils.setMasForPagination(request, appointmentService.getSize(selection),
                    CURRENT_PAGE_APPOINTMENT,COUNT_PAGE_APPOINTMENT);
            request.setAttribute("appointments",appointmentService.getAll(selection, null, limitApp));
            return ControllerConstants.PAGE_MEDIC;
        }catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_PATIENT;
        }
    }

    private Map<String,Integer> getSelection(HttpServletRequest request) throws DBException, ValidateException, SQLException {
        Map<String,Integer> selection = new HashMap<>();
        Integer patientId = ControllerUtils.parseID(request,ControllerConstants.PATIENT_ID);
        Integer doctorId;
        doctorId  = ControllerUtils.parseID(request,ControllerConstants.DOCTOR_ID);
        if (patientId!=null) {
            request.setAttribute("patient", patientService.readById(patientId));
            selection.put(ControllerConstants.PATIENT_ID,patientId);
        }if (doctorId!=null) {
            selection.put(ControllerConstants.DOCTOR_ID,doctorId);
        }
        return selection;
    }
}

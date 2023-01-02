package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
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

/**
 * The class implements working in medic interface
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class MedicCommand implements ActionCommand {
    private final Service<Patient> patientService= PatientService.getPatientService();
    private final Service<Schedule> scheduleService= ScheduleService.getScheduleService();
    private final Service<Appointment> appointmentService= AppointmentService.getAppointmentService();

    /**
     * <p>This method generates a page or path with a response to the client when the doctor or nurse are working in the main interface.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {
        Map<String,Integer> selection = new HashMap<>();
        ControllerUtils.setAttributes(request,"patient_id","doctor_id");
        try {
            request.setAttribute("patients",patientService.getAll(null,null));
            Integer patientId = ControllerUtils.parseID(request,"patient_id");
            Integer doctorId;
            doctorId  = ControllerUtils.parseID(request,"doctor_id");
            if (patientId!=null) {
                request.setAttribute("patient", patientService.readById(patientId));
                selection.put("patient_id",patientId);
                //
            }if (doctorId!=null) {
                selection.put("doctor_id",doctorId);
                //request.setAttribute("patient",patientService.readById(patientId));
            }
            request.setAttribute("schedules",((ScheduleService) scheduleService).getAll(selection));
            request.setAttribute("appointments",((AppointmentService)appointmentService).getAll(selection));
            return ControllerConstants.PAGE_MEDIC;
        }catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_PATIENT;
        }
    }
}

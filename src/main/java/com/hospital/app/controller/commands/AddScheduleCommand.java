package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.dto.ScheduleDto;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Patient;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.DoctorService;
import com.hospital.app.service.impl.PatientService;
import com.hospital.app.service.impl.ScheduleService;
import com.hospital.app.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * The class implements a command for creating and editing a schedule
 *Please see the {@link Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class AddScheduleCommand implements ActionCommand {
    private Service<ScheduleDto> scheduleService = ScheduleService.getScheduleService();
    private Service<Patient> patientService= PatientService.getPatientService();
    private Service<Doctor> doctorService= DoctorService.getDoctorService();

    /**
     * <p>This method generates a page or path with a response to the client when creating or editing a schedule.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when creating or editing a schedule.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException {
        ControllerUtils.setPathReturn(request);
        Integer id = ControllerUtils.parseID(request,Fields.ID);
        ControllerUtils.setAttributes(request, Fields.ID, Fields.VISIT_TIME, ControllerConstants.MESSAGE);
        request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll());
        request.setAttribute(ControllerConstants.PATIENTS, patientService.getAll());
        try {
            ScheduleDto scheduleDto = getScheduleDto(request, id);
            if (request.getParameter(ControllerConstants.SUBMIT)==null) {
                return ControllerConstants.PAGE_ADD_SCHEDULE;
            } else {
                if (id==null){
                    scheduleService.create(scheduleDto);
                }
                else{
                    scheduleDto.setId(id);
                    scheduleService.update(scheduleDto);
                }
                return request.getParameter("path_return");
            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("not_correct")+": "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_ADD_SCHEDULE;
        }
    }


    private ScheduleDto getScheduleDto(HttpServletRequest request, Integer id) throws ParseException, DBException, ValidateException, SQLException {

        ScheduleDto schedule = (ScheduleDto) request.getAttribute("schedule");
        if (id!=null&&request.getParameter("isFirst")!=null){
            schedule = scheduleService.readById(id);
            request.setAttribute(Fields.VISIT_TIME,schedule.getDateVisit().toString());
        }
        else if (schedule==null) {
            ControllerUtils.setAttributes(request,ControllerConstants.PATIENT_ID,ControllerConstants.DOCTOR_ID);
            Integer patientId= ControllerUtils.parseID(request,ControllerConstants.PATIENT_ID);
            Integer doctorId= ControllerUtils.parseID(request,ControllerConstants.DOCTOR_ID);
            Date dateVisit = ControllerUtils.getDateByString(request.getParameter(Fields.VISIT_TIME), true);
            schedule = ScheduleDto.createScheduleDto(doctorId, patientId, dateVisit);
        }

        request.setAttribute("schedule", schedule);
        return schedule;
    }


}


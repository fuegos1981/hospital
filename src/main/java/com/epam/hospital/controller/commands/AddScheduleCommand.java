package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.dto.ScheduleDto;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ScheduleService;
import com.epam.hospital.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * The class implements a command for creating and editing a schedule
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
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
        Integer id = ControllerUtils.parseID(request,Fields.ID);
        setPath(request);
        ControllerUtils.setAttributes(request, Fields.ID,Fields.IS_PATIENT, Fields.VISIT_TIME);
        request.setAttribute(ControllerConstants.DOCTORS, doctorService.getAll(null,null,null));
        request.setAttribute(ControllerConstants.PATIENTS, patientService.getAll(null, null,null));
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
                if (Boolean.parseBoolean(request.getParameter(Fields.IS_PATIENT)))
                    return "/hospital/readPatient?id="+scheduleDto.getPatientId()+"&command=patient_info";
                else {
                    return "/hospital/medic?doctor_id=" + scheduleDto.getDoctorId() + "&command=medic";
                }

            }
        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("not_correct")+": "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_ADD_SCHEDULE;
        }
    }

    private void setPath(HttpServletRequest request) {
        if (request.getAttribute("path")==null){

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


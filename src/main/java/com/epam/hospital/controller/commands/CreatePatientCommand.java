package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;


public class CreatePatientCommand implements ActionCommand {
    private final static Service<Patient> patientService= PatientService.getPatientService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {

        try {
            Integer id = ControllerUtils.parseID(request,Fields.ID);
            //request.setAttribute("title",id==null?"create":"edit");
            Patient patient = getPatient(request, id);
            ControllerUtils.setAttributes(request,Fields.ID);
            if (request.getParameter(ControllerConstants.SUBMIT) == null ){
                return ControllerConstants.PAGE_EDIT_PATIENT;
            }
            if (id==null){
                patientService.create(patient);
                return "/hospital/main?command=admin";
            }
            else{
                patient.setId(id);
                patientService.update(patient);
                return "/hospital/readPatient?id="+id+"&command=patient_info";
            }

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_PATIENT;
        } catch (DBException | ParseException|SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

    public static Patient getPatient(HttpServletRequest request, Integer id) throws ParseException, DBException, ValidateException, SQLException {
        Patient patient;
        if (id!=null&&request.getParameter("not_first")==null){
            patient = patientService.readById(id);
            request.setAttribute(ControllerConstants.GENDER,patient.getGender().toString().toLowerCase());
            request.setAttribute("not_first", "");

        }
        else {
            ControllerUtils.setGender(request);
            patient = Patient.createPatient(request.getParameter(Fields.LAST_NAME),
                    request.getParameter(Fields.FIRST_NAME),
                    ControllerUtils.getDateByString(request.getParameter(Fields.PATIENT_BIRTHDAY), false),
                    request.getParameter(Fields.PATIENT_EMAIL),
                    Gender.valueOf(request.getAttribute(ControllerConstants.GENDER).toString().toUpperCase()));

        }
        request.setAttribute("patient", patient);

        return patient;

    }


}

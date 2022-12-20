package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;


public class CreatePatientCommand implements ActionCommand {
    private final static Service<Patient> patientService= PatientService.getPatientService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {

        try {
            if (request.getParameter(ControllerConstants.LAST_NAME)==null){
                ControllerUtils.setGender(request);
                return ControllerConstants.PAGE_EDIT_PATIENT;
            }
            patientService.create(Patient.createPatient(ControllerUtils.getPerson(request)));
            ControllerUtils.RemoveAttributes(request, ControllerConstants.LAST_NAME,
                    ControllerConstants.FIRST_NAME, Fields.PERSON_BIRTHDAY,Fields.PERSON_EMAIL,
                    ControllerConstants.MESSAGE,ControllerConstants.GENDER);
            return new AdminCommand().execute(request, currentMessageLocale);
        } catch (ValidateException e) {
            ControllerUtils.setAttributes(request, ControllerConstants.LAST_NAME,ControllerConstants.FIRST_NAME,
                    Fields.PERSON_BIRTHDAY,Fields.PERSON_EMAIL,ControllerConstants.GENDER);
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString(ControllerConstants.ERROR_LOG_PASS));
            return ControllerConstants.PAGE_EDIT_PATIENT;

        } catch (DBException | ParseException e) {
            ControllerUtils.RemoveAttributes(request, ControllerConstants.LAST_NAME,ControllerConstants.FIRST_NAME,
                    Fields.PERSON_BIRTHDAY,Fields.PERSON_EMAIL,ControllerConstants.GENDER);
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }


}

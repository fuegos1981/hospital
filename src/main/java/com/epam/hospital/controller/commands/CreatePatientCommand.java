package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
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
            if (request.getParameter("lastName")==null){
                String gender = request.getParameter("gender");
                gender=(gender==null)?"male":gender;
                request.setAttribute("gender",gender);
                return ControllerConstants.PAGE_EDIT_PATIENT;
            }
            patientService.create(Patient.createPatient(ControllerUtils.getPerson(request)));
            ControllerUtils.RemoveAttributes(request, "lastName","firstName","birthday",ControllerConstants.MESSAGE,"gender");
            return new AdminCommand().execute(request, currentMessageLocale);
        } catch (ValidateException e) {
            ControllerUtils.setAttributes(request, "lastName","firstName","birthday","gender");
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("error_log_pass"));
            return ControllerConstants.PAGE_EDIT_PATIENT;

        } catch (DBException | ParseException e) {
            ControllerUtils.RemoveAttributes(request, "lastName","firstName","birthday","gender");
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

}

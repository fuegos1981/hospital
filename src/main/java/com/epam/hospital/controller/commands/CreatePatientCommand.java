package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Person;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreatePatientCommand implements ActionCommand {
    private final static Service<Patient> patientService= PatientService.getPatientService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {

        try {
            Person person = Person.createPerson(request.getParameter("lastName"),request.getParameter("firstName"),
                    new Date(request.getParameter("birthday")),request.getParameter("email"), Gender.MALE);
            patientService.create(Patient.createPatient(person));
            return new AdminCommand().execute(request, currentMessageLocale);
        } catch (ValidateException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";

        } catch (DBException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";
        }
    }
}

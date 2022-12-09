package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class PatientInfoCommand implements ActionCommand {
    private final static Service<Patient> patientService= PatientService.getPatientService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("patient",patientService.readById(id));

            return "/WEB-INF/pages/patient-info.jsp";
        } catch (DBException | SQLException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";
        }

    }
}

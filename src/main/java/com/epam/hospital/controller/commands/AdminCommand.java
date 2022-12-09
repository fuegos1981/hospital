package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class AdminCommand implements ActionCommand {
    private final static Service<Patient> patientService;
    private final static Service<Doctor> doctorService;
    private final static String NAME_ASC = "name asc";
    static {
        doctorService = DoctorService.getDoctorService();
        patientService = PatientService.getPatientService();
    }
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        try {
            String submit = request.getParameter("submit");
            if (submit!=null&&submit.equals("createPatient")){
                return "/WEB-INF/pages/edit-patient.jsp";
            }
            if (submit!=null&&submit.equals("createDoctor")){
                return "/WEB-INF/pages/edit-doctor.jsp";
            }
            String sortPatient = request.getParameter("sortPatient");
            if (sortPatient==null){
                sortPatient = NAME_ASC;
            }
            String sortDoctor = request.getParameter("sortDoctor");
            if (sortDoctor==null){
                sortDoctor = NAME_ASC;
            }
            request.setAttribute("sortPatient",sortPatient);
            request.setAttribute("patients",patientService.getAll(sortPatient));
            request.setAttribute("doctors",doctorService.getAll(sortDoctor));
            return "/WEB-INF/pages/adminInterface.jsp";
        } catch (DBException | SQLException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";
        }
    }
}

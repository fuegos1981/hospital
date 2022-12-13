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
import java.util.List;

public class AdminCommand implements ActionCommand {
    private final static Service<Patient> patientService;
    private final static Service<Doctor> doctorService;
    private final static String NAME_ASC = "name asc";
    private final int maxCountPatient = 10;
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
            fillPatients(request);
            fillDoctors(request);
            return "/WEB-INF/pages/adminInterface.jsp";
        } catch (DBException | SQLException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";
        }
    }

    private void fillDoctors(HttpServletRequest request) throws DBException, SQLException {
        String sortDoctor = request.getParameter("sortDoctor");
        sortDoctor=(sortDoctor==null)?NAME_ASC:sortDoctor;
        request.setAttribute("sortDoctor",sortDoctor);
        request.setAttribute("doctors",doctorService.getAll(sortDoctor));
    }

    private void fillPatients(HttpServletRequest request) throws DBException, SQLException {
        String sortPatient = request.getParameter("sortPatient");

        sortPatient=(sortPatient==null)?NAME_ASC:sortPatient;
        request.setAttribute("sortPatient",sortPatient);
        int currentPagePatient;
        if (request.getParameter("currentPagePatient")==null){
            currentPagePatient=1;
        }
        else {
            currentPagePatient = Integer.parseInt(request.getParameter("currentPagePatient"));
        }
        List<Patient> patients =patientService.getAll(sortPatient);
        int countPatients= patients.size();
        int countPagePatient = (countPatients<10)?1:(int)Math.ceil(1.00*countPatients/10);
        request.setAttribute("countPagePatient", countPagePatient);
        if (patients.size()<=maxCountPatient){
            request.setAttribute("patients",patients);
        }
        else{
            int start = (currentPagePatient-1)*10;
            int end = Math.min(currentPagePatient*10-1,patients.size()-1);
            request.setAttribute("patients",patients.subList(start, end));
        }
        request.setAttribute("currentPagePatient",currentPagePatient);


    }
}

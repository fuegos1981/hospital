package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
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
    private final static String SORT_DOCTOR = "sortDoctor";
    private final static String SORT_PATIENT = "sortPatient";
    private final static String CURRENT_PAGE_DOCTOR = "current_page_doctor";
    private final static String CURRENT_PAGE_PATIENT = "current_page_patient";
    private final static String COUNT_PAGE_DOCTOR = "count_page_doctor";
    private final static String COUNT_PAGE_PATIENT = "count_page_patient";

    static {
        doctorService = DoctorService.getDoctorService();
        patientService = PatientService.getPatientService();
    }
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        try {
            fillPatients(request);
            fillDoctors(request);
            return ControllerConstants.PAGE_ADMIN;
        } catch (DBException | SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

    private void fillDoctors(HttpServletRequest request) throws DBException, SQLException {
        String sortDoctor = request.getParameter(SORT_DOCTOR);
        sortDoctor=(sortDoctor==null)?NAME_ASC:sortDoctor;
        request.setAttribute(SORT_DOCTOR,sortDoctor);
        int[] limit = ControllerUtils.setMasForPagination(request, doctorService.getSize(),CURRENT_PAGE_DOCTOR,COUNT_PAGE_DOCTOR);
        List<Doctor> doctors =doctorService.getAll(limit,sortDoctor);
        request.setAttribute(ControllerConstants.DOCTORS,doctors);

    }

    private void fillPatients(HttpServletRequest request) throws DBException, SQLException {
        String sortPatient = request.getParameter(SORT_PATIENT);
        sortPatient=(sortPatient==null)?NAME_ASC:sortPatient;
        request.setAttribute(SORT_PATIENT,sortPatient);
        int[] limit = ControllerUtils.setMasForPagination(request, patientService.getSize(),CURRENT_PAGE_PATIENT,COUNT_PAGE_PATIENT);
        List<Patient> patients =patientService.getAll(limit,sortPatient);
        request.setAttribute(ControllerConstants.PATIENTS,patients);
    }

}

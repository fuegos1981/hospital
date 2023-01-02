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

/**
 * The class implements working in Admin interface
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class AdminCommand implements ActionCommand {
    private final static Service<Patient> patientService = PatientService.getPatientService();
    private final static Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final static String NAME_ASC = "name asc";
    private final static String SORT_DOCTOR = "sortDoctor";
    private final static String SORT_PATIENT = "sortPatient";
    private final static String CURRENT_PAGE_DOCTOR = "current_page_doctor";
    private final static String CURRENT_PAGE_PATIENT = "current_page_patient";
    private final static String COUNT_PAGE_DOCTOR = "count_page_doctor";
    private final static String COUNT_PAGE_PATIENT = "count_page_patient";

    /**
     * <p>This method generates a page or path with a response to the client when the administrator is working in the main interface.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {
       request.getSession().removeAttribute("patient");
       fillPatients(request);
       fillDoctors(request);
            return ControllerConstants.PAGE_ADMIN;

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

package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.QueryRedactor;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.SimpleService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class implements working in Admin interface
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class AdminCommand implements ActionCommand {
    private final static Service<Patient> patientService = PatientService.getPatientService();
    private final static Service<Doctor> doctorService = DoctorService.getDoctorService();
    private final SimpleService categoryService = SimpleService.getSimpleService(Constants.CATEGORY);
    private final static String SORT_DOCTOR = "sortDoctor";
    private final static String SORT_PATIENT = "sortPatient";
    private final static String CURRENT_PAGE_DOCTOR = "current_page_doctor";
    private final static String CURRENT_PAGE_PATIENT = "current_page_patient";
    private final static String COUNT_PAGE_DOCTOR = "count_page_doctor";
    private final static String COUNT_PAGE_PATIENT = "count_page_patient";

    /**
     * <p>This method generates a page or path with a response to the client when the administrator is working in the main interface.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException {

       fillPatients(request);
       fillDoctors(request);
            return ControllerConstants.PAGE_ADMIN;

    }

    private void fillDoctors(HttpServletRequest request) throws DBException, SQLException {
        ControllerUtils.setAttributes(request,ControllerConstants.CATEGORY_ID);
        request.setAttribute(ControllerConstants.CATEGORIES, categoryService.getAll());
        Integer categoryId = ControllerUtils.parseID(request,ControllerConstants.CATEGORY_ID);
        Map<String,Object> selection = new HashMap<>();
        if (categoryId!=null) {
            selection.put(ControllerConstants.CATEGORY_ID, categoryId);
        }
        String sortDoctor = request.getParameter(SORT_DOCTOR);
        sortDoctor=(sortDoctor==null)? SortRule.NAME_ASC.toString():sortDoctor;
        request.setAttribute(SORT_DOCTOR,sortDoctor);
        int[] limit = ControllerUtils.setMasForPagination(request, doctorService.getSize(QueryRedactor.getRedactor(selection)),CURRENT_PAGE_DOCTOR,COUNT_PAGE_DOCTOR);
        List<Doctor> doctors =doctorService.getAll(QueryRedactor.getRedactor(selection, SortRule.valueOf(sortDoctor),limit));
        request.setAttribute(ControllerConstants.DOCTORS,doctors);

    }

    private void fillPatients(HttpServletRequest request) throws DBException, SQLException {
        String sortPatient = request.getParameter(SORT_PATIENT);
        sortPatient=(sortPatient==null)?SortRule.NAME_ASC.toString():sortPatient;
        request.setAttribute(SORT_PATIENT,sortPatient);
        int[] limit = ControllerUtils.setMasForPagination(request, patientService.getSize(),CURRENT_PAGE_PATIENT,COUNT_PAGE_PATIENT);
        List<Patient> patients =patientService.getAll(QueryRedactor.getRedactor(SortRule.valueOf(sortPatient), limit));
        request.setAttribute(ControllerConstants.PATIENTS,patients);
    }

}

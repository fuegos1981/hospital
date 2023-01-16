package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.exceptions.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * The class implements a command for creating and editing a patient
 * Please see the {@link com.epam.hospital.service.Service}  for true identity
 *
 * @author Sinkevych Olena
 */
public class CreatePatientCommand implements ActionCommand {
    public Service<Patient> patientService = PatientService.getPatientService();

    /**
     * <p>This method generates a page or path with a response to the client when creating or editing a patient.
     * </p>
     *
     * @param request              is as an argument to the servlet's service methods (doGet, doPost...).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return String page or path with a response to the client when creating or editing a patient.
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException {

        try {
            ControllerUtils.setAttributes(request, Fields.ID, ControllerConstants.GENDER);
            Integer id = ControllerUtils.parseID(request, Fields.ID);
            Patient patient = getPatient(request, id);
            if (request.getParameter(ControllerConstants.SUBMIT) == null) {
                return ControllerConstants.PAGE_EDIT_PATIENT;
            }
            if (id == null) {
                patientService.create(patient);
                return "/hospital/Admin?command=admin";
            } else {
                patient.setId(id);
                patientService.update(patient);
                return "/hospital/readPatient?id=" + id + "&command=patient_info";
            }

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, currentMessageLocale.getString("not_correct") + " " + currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_PATIENT;
        }
    }

    private Patient getPatient(HttpServletRequest request, Integer id) throws ParseException, DBException, ValidateException, SQLException {

        Patient patient;
        if (id != null && request.getParameter("isFirst") != null) {
            patient = patientService.readById(id);
            request.setAttribute(Fields.PATIENT_BIRTHDAY, patient.getBirthday().toString());
            request.setAttribute(ControllerConstants.GENDER, patient.getGender().toString());
        } else {
            if (request.getAttribute(ControllerConstants.GENDER) == null) {
                request.setAttribute(ControllerConstants.GENDER,Gender.MALE.toString());

            }
            ControllerUtils.setAttributes(request, Fields.PATIENT_BIRTHDAY);
            patient = Patient.createPatient(request.getParameter(Fields.LAST_NAME),
                    request.getParameter(Fields.FIRST_NAME),
                    ControllerUtils.getDateByString(request.getParameter(Fields.PATIENT_BIRTHDAY), false),
                    request.getParameter(Fields.PATIENT_EMAIL),
                    Gender.valueOf(((String) request.getAttribute(ControllerConstants.GENDER))));
            if (id != null)
                patient.setId(id);
        }
        request.setAttribute("patient", patient);
        return patient;
    }


}

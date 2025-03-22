package com.hospital.app.controller.commands;

import com.hospital.app.MessageManager;
import com.hospital.app.controller.ActionCommand;
import com.hospital.app.controller.ControllerConstants;
import com.hospital.app.controller.ControllerUtils;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Category;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Role;
import com.hospital.app.repository.Constants;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.SortRule;
import com.hospital.app.service.Service;
import com.hospital.app.service.impl.DoctorService;
import com.hospital.app.service.impl.SimpleService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * The class implements a command for creating and editing a doctor
 * Please see the {@link Service}  for true identity
 *
 * @author Sinkevych Olena
 */
public class CreateDoctorCommand implements ActionCommand {
    private Service<Doctor> doctorService = DoctorService.getDoctorService();
    private SimpleService categoryService = SimpleService.getSimpleService(Constants.CATEGORY);

    /**
     * <p>This method generates a page or path with a response to the client when creating or editing a doctor.
     * </p>
     *
     * @param request              is as an argument to the servlet's service methods (doGet, doPost...).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return String page or path with a response to the client when creating or editing a doctor.
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException {

        try {
            Integer id = ControllerUtils.parseID(request, Fields.ID);
            ControllerUtils.setAttributes(request, Fields.ID, ControllerConstants.MESSAGE, ControllerConstants.ROLE);
            request.setAttribute(ControllerConstants.CATEGORIES, categoryService.getAll(QueryRedactor.getRedactor(SortRule.NAME_SIMPLE_ASC)));
            Doctor doctor = getDoctor(request, id);
            if (request.getParameter(ControllerConstants.SUBMIT) == null) {
                return ControllerConstants.PAGE_EDIT_DOCTOR;
            }
            if (id == null) {
                doctorService.create(doctor);
            } else {
                doctor.setId(id);
                doctorService.update(doctor);
            }
            return "/hospital/main?command=admin";

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct") + " " + currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_DOCTOR;

        }
    }

    private Doctor getDoctor(HttpServletRequest request, Integer id) throws DBException, ValidateException, SQLException {
        Doctor doctor;
        if (id != null && request.getParameter("isFirst") != null) {
            doctor = doctorService.readById(id);
            request.setAttribute(ControllerConstants.ROLE, doctor.getRole().toString());
            request.setAttribute(Fields.PASSWORD, Fields.PASSWORD_NOT_CHANGE);
        } else {
            if (request.getAttribute(ControllerConstants.ROLE) == null) {
                request.setAttribute(ControllerConstants.ROLE, Role.NURSE.toString());
            }
            ControllerUtils.setAttributes(request, ControllerConstants.CATEGORY_ID, Fields.PASSWORD);
            Category category = (Category) categoryService.readById(ControllerUtils.parseID(request, ControllerConstants.CATEGORY_ID));
            doctor = Doctor.createDoctor(request.getParameter(Fields.LAST_NAME),
                    request.getParameter(Fields.FIRST_NAME), category);
            doctor.setLogin(request.getParameter(Fields.LOGIN));
            doctor.setPassword(request.getParameter(Fields.PASSWORD));
            doctor.setRole(Role.valueOf((String) request.getAttribute(ControllerConstants.ROLE)));
        }
        request.setAttribute("doctor", doctor);
        return doctor;
    }
}

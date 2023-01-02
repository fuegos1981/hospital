package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.*;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.SimpleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * The class implements a command for creating and editing a doctor
 *Please see the {@link com.epam.hospital.service.Service}  for true identity
 * @author Sinkevych Olena
 *
 */
public class CreateDoctorCommand implements ActionCommand {
    private Service<Doctor> doctorService= DoctorService.getDoctorService();
    private SimpleService categoryService=SimpleService.getSimpleService(Constants.CATEGORY);

    /**
     * <p>This method generates a page or path with a response to the client when creating or editing a doctor.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost, etc).
     * @param currentMessageLocale is current locale, used to display error messages in the given locale.
     * @return  String page or path with a response to the client when creating or editing a doctor.
     *
     */
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) throws DBException, SQLException, ParseException {

        try {
            Integer id = ControllerUtils.parseID(request,Fields.ID);
            ControllerUtils.setAttributes(request,Fields.ID, ControllerConstants.MESSAGE);
            request.setAttribute(ControllerConstants.CATEGORIES, categoryService.getAll(null,null));
            Doctor doctor = getDoctor(request, id);
            if (request.getParameter(ControllerConstants.SUBMIT) == null ) {
                return ControllerConstants.PAGE_EDIT_DOCTOR;
            }
            if (id==null){
                doctorService.create(doctor);
            }
            else{
                doctor.setId(id);
                doctorService.update(doctor);
            }
            return "/hospital/main?command=admin";

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_DOCTOR;

        }
    }

    private Doctor getDoctor(HttpServletRequest request, Integer id) throws DBException, ValidateException, SQLException {
        Doctor  doctor=(Doctor) request.getSession().getAttribute("doctor");
        if (doctor!=null&&request.getParameter("isFirst")!=null){

        }
        else if (id!=null){
            doctor = doctorService.readById(id);
        }
        else if (doctor==null) {
                ControllerUtils.setAttributes(request, ControllerConstants.CATEGORY_ID, "password");
                Category category = (Category) categoryService.readById(ControllerUtils.parseID(request, ControllerConstants.CATEGORY_ID));
                doctor = Doctor.createDoctor(request.getParameter(Fields.LAST_NAME),
                        request.getParameter(Fields.FIRST_NAME), category);
                doctor.setLogin(request.getParameter(Fields.LOGIN));
                doctor.setPassword(request.getParameter(Fields.PASSWORD));
                doctor.setRole(Role.NURSE);
            }
        else {
            doctor.setRole(Role.valueOf(request.getParameter(ControllerConstants.ROLE).toUpperCase()));
        }
        request.getSession().setAttribute("doctor", doctor);

        return doctor;
    }
}

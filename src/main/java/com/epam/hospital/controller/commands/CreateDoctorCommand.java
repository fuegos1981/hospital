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


public class CreateDoctorCommand implements ActionCommand {
    private Service<Doctor> doctorService= DoctorService.getDoctorService();
    private SimpleService categoryService=SimpleService.getSimpleService(Constants.CATEGORY);
    private SimpleService roleService=SimpleService.getSimpleService(Constants.ROLE);
    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        String category_id=request.getParameter(ControllerConstants.CATEGORY_ID);
        setRole(request);
        try {
            request.setAttribute(ControllerConstants.CATEGORIES, categoryService.getAll(null,null));
            request.setAttribute(ControllerConstants.CATEGORY_ID,category_id);
            ControllerUtils.setGender(request);
            ControllerUtils.setAttributes(request, Fields.LAST_NAME,Fields.FIRST_NAME,
                    Fields.LOGIN,Fields.PASSWORD);
            if (request.getParameter(ControllerConstants.SUBMIT) == null ) {
                return ControllerConstants.PAGE_EDIT_DOCTOR;
            }
            else {
                if (category_id==null||category_id.isEmpty()){
                    throw new ValidateException("select category!");
                }
                Category category = (Category) categoryService.readById(Integer.parseInt(category_id));
                doctorService.create(getDoctor(request, category, Role.valueOf(request.getParameter(ControllerConstants.ROLE))));
                ControllerUtils.RemoveAttributes(request, Fields.LAST_NAME,Fields.FIRST_NAME,
                        Fields.LOGIN,Fields.PASSWORD,Fields.ROLE_NAME);
                return new AdminCommand().execute(request, currentMessageLocale);
            }

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE,
                    currentMessageLocale.getString("not_correct")+" "+currentMessageLocale.getString(e.getMessage()));
            return ControllerConstants.PAGE_EDIT_DOCTOR;

        } catch (DBException | ParseException |SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }

    public static Doctor getDoctor(HttpServletRequest request, Category category, Role role) throws ParseException {
        Doctor doctor= Doctor.createDoctor(request.getParameter(Fields.LAST_NAME),
                request.getParameter(Fields.FIRST_NAME), category);
        doctor.setLogin(request.getParameter(Fields.LOGIN));
        doctor.setPassword(request.getParameter(Fields.PASSWORD));
        doctor.setRole(role);
        return doctor;
    }

    public static void setRole(HttpServletRequest request) {
        String role = request.getParameter(ControllerConstants.ROLE);
        role=(role==null)?ControllerConstants.NURSE:role;
        request.setAttribute(ControllerConstants.ROLE,role);
    }
}

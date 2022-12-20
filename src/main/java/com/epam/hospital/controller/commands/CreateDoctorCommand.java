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
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.SimpleService;
import com.epam.hospital.service.impl.ValidateException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;


public class CreateDoctorCommand implements ActionCommand {
    private Service<Doctor> doctorService= DoctorService.getDoctorService();
    private SimpleService categoryService=SimpleService.getSimpleService(Constants.CATEGORY);

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        String category_id=request.getParameter(ControllerConstants.CATEGORY_ID);
        try {
            request.setAttribute(ControllerConstants.CATEGORIES, categoryService.getAll(null));
            request.setAttribute(ControllerConstants.CATEGORY_ID,category_id);
            ControllerUtils.setGender(request);
            ControllerUtils.setAttributes(request, ControllerConstants.LAST_NAME,ControllerConstants.FIRST_NAME,
                    Fields.PERSON_BIRTHDAY,Fields.PERSON_EMAIL);
            if (request.getParameter(ControllerConstants.SUBMIT) == null ) {
                return ControllerConstants.PAGE_EDIT_DOCTOR;
            }
            else {
                if (category_id==null){
                    throw new ValidateException("select category!");
                }
                Category category = (Category) categoryService.readById(Integer.parseInt(category_id));
                doctorService.create(Doctor.createDoctor(ControllerUtils.getPerson(request), category));
                ControllerUtils.RemoveAttributes(request, ControllerConstants.LAST_NAME,ControllerConstants.FIRST_NAME,
                        Fields.PERSON_BIRTHDAY,Fields.PERSON_EMAIL,ControllerConstants.GENDER);
                return new AdminCommand().execute(request, currentMessageLocale);
            }

        } catch (ValidateException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_EDIT_DOCTOR;

        } catch (DBException | ParseException |SQLException e) {
            request.setAttribute(ControllerConstants.MESSAGE, e.getMessage());
            return ControllerConstants.PAGE_ERROR;
        }
    }
}

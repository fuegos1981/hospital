package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.controller.ControllerConstants;
import com.epam.hospital.controller.ControllerUtils;
import com.epam.hospital.model.*;
import com.epam.hospital.repository.DBException;
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
    private SimpleService categoryService=SimpleService.getSimpleService("Category");

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {

        try {

            if (request.getParameter("category_id")==null) {
                request.setAttribute("categories",categoryService.getAll(null));
                return ControllerConstants.PAGE_EDIT_DOCTOR;
            }
            else {
                Category category = (Category) categoryService.readById(Integer.parseInt(request.getParameter("category_id")));
                doctorService.create(Doctor.createDoctor(ControllerUtils.getPerson(request), category));
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

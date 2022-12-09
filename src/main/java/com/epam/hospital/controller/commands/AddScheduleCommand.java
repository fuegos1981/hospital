package com.epam.hospital.controller.commands;

import com.epam.hospital.MessageManager;
import com.epam.hospital.controller.ActionCommand;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class AddScheduleCommand implements ActionCommand {
    private final static Service<Doctor> doctorService = DoctorService.getDoctorService();

    @Override
    public String execute(HttpServletRequest request, MessageManager currentMessageLocale) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            request.setAttribute("name", request.getParameter("name").replace("%20"," "));
            request.setAttribute("doctors",doctorService.getAll(""));
            return "/WEB-INF/pages/add-schedule.jsp";
        } catch (SQLException|DBException e) {
            request.setAttribute("message", e.getMessage());
            return "/WEB-INF/pages/error.jsp";
        }
    }
}

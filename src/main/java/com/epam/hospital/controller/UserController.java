package com.epam.hospital.controller;

import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;
import com.epam.hospital.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/main")
public class UserController extends HttpServlet {
    private static Service<User> userService;
    private static Service<Patient> patientService;

    @Override
    public void init() throws ServletException {
        userService = UserService.getUserService();
        patientService = PatientService.getPatientService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User user = ((UserService)userService).readByName(login);
            if (user== null||!user.getPassword().equals(password)){
                req.setAttribute("invalid", "invalid login or password");
                req.getRequestDispatcher("WEB-INF/index.jsp").forward(req,resp);
            }
            else{
                req.setAttribute("patients",patientService.getAll());
                req.getRequestDispatcher("WEB-INF/pages/adminInterface.jsp").forward(req,resp);
            }
        } catch (DBException | SQLException e) {
            req.setAttribute("message", e.getMessage());
            resp.setStatus(404);
            resp.sendRedirect("/hospital/error");
            //req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("invalid", "");
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }

}

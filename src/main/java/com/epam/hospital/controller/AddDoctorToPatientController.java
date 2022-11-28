package com.epam.hospital.controller;

import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addDoctorToPatient")
public class AddDoctorToPatientController extends HttpServlet {
    private static Service<Doctor> doctorService;

    @Override
    public void init() throws ServletException {
        doctorService = DoctorService.getDoctorService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            req.setAttribute("name", req.getParameter("name").replace("%20"," "));
            req.setAttribute("doctors",doctorService.getAll());
            req.getRequestDispatcher("WEB-INF/pages/add-doctor-to-patient.jsp").forward(req,resp);
        } catch (DBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            req.setAttribute("message", e.getMessage());
            resp.setStatus(404);
            resp.sendRedirect("/hospital/error");
        }

    }
}

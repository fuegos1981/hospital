package com.epam.hospital.controller;

import com.epam.hospital.model.Doctor;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.DoctorService;
import com.epam.hospital.service.impl.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class AdministratorController extends HttpServlet {
    private static Service<Patient> patientService;
    private static Service<Doctor> doctorService;

    @Override
    public void init() {
        doctorService = DoctorService.getDoctorService();
        patientService = PatientService.getPatientService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String sortPatient =req.getParameter("sortPatient");
            if (sortPatient ==null)
            req.setAttribute("patients",patientService.getAll(sortPatient));
            req.setAttribute("doctors",doctorService.getAll(req.getParameter("sortDoctor")));
            req.getRequestDispatcher("WEB-INF/pages/adminInterface.jsp").forward(req,resp);
        } catch (DBException| SQLException e) {
            req.setAttribute("message", e.getMessage());
            resp.setStatus(404);
            resp.sendRedirect("/hospital/error");
        }


    }

}

package com.epam.hospital.controller;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Person;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.impl.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
@WebServlet("/create-patient")
public class CreatePatientController extends HttpServlet {
    private static Service<Patient> patientService;

    @Override
    public void init(){
        patientService = PatientService.getPatientService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
/*
        try {
            Person person = Person.createPerson(req.getParameter("lastName"),req.getParameter("firstName"),
                    new Date(req.getParameter("birthday")),req.getParameter("email"), Gender.MALE);
           // patientService.create(Patient.createPatient(person));
            resp.sendRedirect("/hospital/administrator");
        } catch (DBException e) {
            req.setAttribute("message", e.getMessage());
            resp.setStatus(404);
            resp.sendRedirect("/hospital/error");
        }
*/
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/edit-patient.jsp").forward(req,resp);
    }
}

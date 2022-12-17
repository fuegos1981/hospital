package com.epam.hospital.service.impl;

import com.epam.hospital.model.Appointment;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.AppointmentRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AppointmentService implements Service<Appointment> {
    private static AppointmentService appointmentService;
    private AppointmentRepository appointmentRepository;
    private AppointmentService() {
        this.appointmentRepository = appointmentRepository.getRepository();
    }
    public static AppointmentService getAppointmentService(){
        return Objects.requireNonNullElseGet(appointmentService, AppointmentService::new);
    }
    @Override
    public boolean create(Appointment appointment) throws DBException {
        return appointmentRepository.create(appointment);
    }

    @Override
    public Appointment readById(int id) throws DBException, SQLException {
        return  appointmentRepository.readByID(id);
    }

    @Override
    public boolean update(Appointment appointment) throws DBException {
        return appointmentRepository.create(appointment);
    }

    @Override
    public void delete(Appointment appointment) throws DBException {
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> getAll(String sortRule) throws DBException, SQLException {
        return appointmentRepository.getAllAppointments();
    }

    public List<Appointment> readAppointmentsByPatientID(int id) throws DBException, SQLException {
        return  appointmentRepository.readAppointmentByPatientID(id);
    }

}

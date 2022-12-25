package com.epam.hospital.service.impl;

import com.epam.hospital.model.Appointment;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.AppointmentRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
    public boolean create(Appointment appointment) throws DBException, ValidateException{
        validApp(appointment);
        return appointmentRepository.create(appointment);
    }

    private void validApp(Appointment appointment) throws ValidateException {
        if (appointment.getPatient()==null) throw new ValidateException("patient");
        if (appointment.getDoctor()==null) throw new ValidateException("doctor");
        if (appointment.getDiagnosis()==null) throw new ValidateException("diagnosis");
        if (appointment.getMedication().isEmpty()&& appointment.getProcedure().isEmpty()&& appointment.getOperation().isEmpty())
            throw new ValidateException("description");
    }

    @Override
    public Appointment readById(Integer id) throws DBException, SQLException, ValidateException {
        return  appointmentRepository.readByID(id);
    }

    @Override
    public boolean update(Appointment appointment) throws DBException, ValidateException {
        validApp(appointment);
        return appointmentRepository.updateAppointment(appointment);
    }

    @Override
    public void delete(Appointment appointment) throws DBException {
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> getAll(int[] limit,String sortRule) throws DBException, SQLException {
        return appointmentRepository.getAllAppointments();
    }

    public List<Appointment> getAll(Map<String, Integer> selection) throws DBException, SQLException {
        return appointmentRepository.getAllAppointments(selection);
    }
    @Override
    public int getSize() throws DBException {
       return appointmentRepository.getSize();
    }

    public List<Appointment> readAppointmentsByPatientID(int id) throws DBException, SQLException {
        return  appointmentRepository.readAppointmentByPatientID(id);
    }



}

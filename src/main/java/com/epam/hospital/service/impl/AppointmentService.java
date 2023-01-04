package com.epam.hospital.service.impl;

import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.SortRule;
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
    public Appointment readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null) {
            throw new ValidateException("appointment");
        }
        return  appointmentRepository.readByID(id);
    }

    @Override
    public boolean create(Appointment appointment) throws DBException, ValidateException {
        checkAppointment(appointment);
        return appointmentRepository.create(appointment);
    }

    @Override
    public boolean update(Appointment appointment) throws DBException, ValidateException {
        checkAppointment(appointment);
        return appointmentRepository.updateAppointment(appointment);
    }

    @Override
    public void delete(Appointment appointment) throws DBException {
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> getAll(Map<String, Integer> selection,SortRule sortRule,int[] limit) throws DBException, SQLException {
        return appointmentRepository.getAllAppointments(selection, sortRule, limit);
    }

    @Override
    public int getSize(Map<String,Integer> selection) throws DBException {
        return appointmentRepository.getSize(selection);
    }

    private void checkAppointment(Appointment appointment) throws ValidateException {
        if (appointment.getPatient()==null)
            throw new ValidateException("patient");
        if (appointment.getDoctor()==null)
            throw new ValidateException("doctor");
        if (appointment.getDiagnosis()==null)
            throw new ValidateException("diagnosis");
        if (appointment.getMedication().isEmpty()&& appointment.getProcedure().isEmpty()&& appointment.getOperation().isEmpty())
            throw new ValidateException("description");
    }









}

package com.hospital.app.service.impl;

import com.hospital.app.dto.AppointmentDto;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.elements.AppointmentRepository;
import com.hospital.app.service.MappingUtils;
import com.hospital.app.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService implements Service<AppointmentDto> {
    private AppointmentRepository appointmentRepository;
    private final MappingUtils mappingUtils;

    private AppointmentService() {
        this.appointmentRepository = AppointmentRepository.getRepository();
        this.mappingUtils = new MappingUtils();
    }

    public static AppointmentService getAppointmentService() {
        return new AppointmentService();
    }

    @Override
    public AppointmentDto readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null) {
            throw new ValidateException("appointment");
        }
        return mappingUtils.mapToAppointmentDto(appointmentRepository.readByID(id));
    }

    @Override
    public boolean create(AppointmentDto appointmentDto) throws DBException, ValidateException, SQLException {
        checkAppointment(appointmentDto);
        return appointmentRepository.create(mappingUtils.mapToAppointment(appointmentDto));
    }

    @Override
    public boolean update(AppointmentDto appointmentDto) throws DBException, ValidateException, SQLException {
        checkAppointment(appointmentDto);
        return appointmentRepository.updateAppointment(mappingUtils.mapToAppointment(appointmentDto));
    }

    @Override
    public void delete(AppointmentDto appointmentDto) throws DBException, ValidateException, SQLException {
        appointmentRepository.delete(mappingUtils.mapToAppointment(appointmentDto));
    }

    @Override
    public List<AppointmentDto> getAll(QueryRedactor qr) throws DBException, SQLException {
        return appointmentRepository.getAllAppointments(qr).stream()
                .map(mappingUtils::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getSize(QueryRedactor qr) throws DBException {
        return appointmentRepository.getSize(qr);
    }

    @Override
    public List<AppointmentDto> getAll() throws DBException, SQLException {
        return appointmentRepository.getAllAppointments().stream()
                .map(mappingUtils::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getSize() throws DBException {
        return appointmentRepository.getSize();
    }

    private void checkAppointment(AppointmentDto appointment) throws ValidateException {
        if (appointment.getPatientId() == null)
            throw new ValidateException("patient");
        if (appointment.getDoctorId() == null)
            throw new ValidateException("doctor");
        if (appointment.getDiagnosisId() == null)
            throw new ValidateException("diagnosis");
        if (appointment.getDescription().isEmpty())
            throw new ValidateException("description");
    }


}

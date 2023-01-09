package com.epam.hospital.service.impl;

import com.epam.hospital.dto.AppointmentDto;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.repository.elements.AppointmentRepository;
import com.epam.hospital.service.MappingUtils;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppointmentService implements Service<AppointmentDto> {
    private AppointmentRepository appointmentRepository;
    private final MappingUtils mappingUtils;

    private AppointmentService() {
        this.appointmentRepository = AppointmentRepository.getRepository();
        this.mappingUtils = new MappingUtils();
    }
    public static AppointmentService getAppointmentService(){
       return new AppointmentService();
    }

    @Override
    public AppointmentDto readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null) {
            throw new ValidateException("appointment");
        }
        return  mappingUtils.mapToAppointmentDto(appointmentRepository.readByID(id));
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
    public List<AppointmentDto> getAll(Map<String, Object> selection,SortRule sortRule,int[] limit) throws DBException, SQLException {
        return appointmentRepository.getAllAppointments(selection, sortRule, limit).stream()
                .map(mappingUtils::mapToAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getSize(Map<String,Object> selection) throws DBException {
        return appointmentRepository.getSize(selection);
    }

    private void checkAppointment(AppointmentDto appointment) throws ValidateException {
        if (appointment.getPatientId()==null)
            throw new ValidateException("patient");
        if (appointment.getDoctorId()==null)
            throw new ValidateException("doctor");
        if (appointment.getDiagnosisId()==null)
            throw new ValidateException("diagnosis");
        if (appointment.getMedication().isEmpty()&& appointment.getProcedure().isEmpty()&& appointment.getOperation().isEmpty())
            throw new ValidateException("description");
    }









}

package com.hospital.app.service.impl;

import com.hospital.app.dto.ScheduleDto;
import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.exceptions.DBException;

import com.hospital.app.repository.Fields;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.elements.ScheduleRepository;
import com.hospital.app.service.MappingUtils;
import com.hospital.app.service.Service;
import com.hospital.app.service.ValidatorUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleService implements Service<ScheduleDto> {

    private ScheduleRepository scheduleRepository;
    private final MappingUtils mappingUtils;

    private ScheduleService() {

        this.scheduleRepository = ScheduleRepository.getRepository();
        this.mappingUtils = new MappingUtils();
    }

    public static ScheduleService getScheduleService() {
        return new ScheduleService();
    }

    @Override
    public boolean create(ScheduleDto scheduleDto) throws DBException, ValidateException, SQLException {
        checkSchedule(scheduleDto);
        return scheduleRepository.create(mappingUtils.mapToSchedule(scheduleDto));
    }

    @Override
    public ScheduleDto readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null)
            throw new ValidateException("schedule");
        return mappingUtils.mapToScheduleDto(scheduleRepository.readByID(id));
    }

    @Override
    public boolean update(ScheduleDto scheduleDto) throws DBException, ValidateException, SQLException {
        checkSchedule(scheduleDto);
        return scheduleRepository.updateSchedule(mappingUtils.mapToSchedule(scheduleDto));
    }

    @Override
    public void delete(ScheduleDto scheduleDto) throws DBException, ValidateException, SQLException {
        scheduleRepository.delete(mappingUtils.mapToSchedule(scheduleDto));
    }

    @Override
    public List<ScheduleDto> getAll(QueryRedactor qr) throws DBException, SQLException {
        return scheduleRepository.getAllSchedules(qr).stream()
                .map(mappingUtils::mapToScheduleDto)
                .collect(Collectors.toList());

    }

    public int getSize(QueryRedactor qr) throws DBException {
        return scheduleRepository.getSize(qr);
    }

    @Override
    public List<ScheduleDto> getAll() throws DBException, SQLException {
        return scheduleRepository.getAllSchedules().stream()
                .map(mappingUtils::mapToScheduleDto)
                .collect(Collectors.toList());
    }

    public int getSize() throws DBException {
        return scheduleRepository.getSize();
    }

    private void checkSchedule(ScheduleDto schedule) throws ValidateException, DBException {
        if (schedule == null) {
            throw new ValidateException("schedule");
        }
        if (schedule.getPatientId() == null) {
            throw new ValidateException("patient");
        }
        if (schedule.getDoctorId() == null) {
            throw new ValidateException("doctor");
        }
        ValidatorUtils.dateVisitValidate(Fields.VISIT_TIME, schedule.getDateVisit());
        checkTime(schedule);
    }

    private void checkTime(ScheduleDto schedule) throws DBException, ValidateException {
        Map<String, Object> selection = new HashMap<>();
        selection.put(Fields.DOCTOR_ID, schedule.getDoctorId());
        selection.put(Fields.VISIT_TIME, schedule.getDateVisit());

        if (scheduleRepository.getAllSchedules(QueryRedactor.getRedactor(selection)).stream().anyMatch(e -> e.getId() != schedule.getId()))
            throw new ValidateException("doctor_not_time");
        selection.remove(Fields.DOCTOR_ID);
        selection.put(Fields.PATIENT_ID, schedule.getPatientId());
        if (scheduleRepository.getAllSchedules(QueryRedactor.getRedactor(selection)).stream().anyMatch(e -> e.getId() != schedule.getId()))
            throw new ValidateException("patient_not_time");

    }

}

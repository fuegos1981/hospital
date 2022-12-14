package com.epam.hospital.service.impl;

import com.epam.hospital.model.Schedule;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.ScheduleRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScheduleService implements Service<Schedule> {

    private static ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;

    private ScheduleService() {
        this.scheduleRepository = scheduleRepository.getRepository();
    }

    public static ScheduleService getScheduleService() {
        return Objects.requireNonNullElseGet(scheduleService, ScheduleService::new);
    }

    @Override
    public boolean create(Schedule schedule) throws DBException {
        return scheduleRepository.create(schedule);
    }

    @Override
    public Schedule readById(int id) throws DBException, SQLException {
        return scheduleRepository.readByID(id);
    }

    @Override
    public boolean update(Schedule schedule) throws DBException {
        return scheduleRepository.create(schedule);
    }

    @Override
    public void delete(Schedule schedule) throws DBException {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> getAll(String sortRule) throws DBException, SQLException {
        return scheduleRepository.getAllSchedules();

    }
    public List<Schedule> getScheduleByPatientId(int id) throws DBException, SQLException {
        return scheduleRepository.getAllSchedules().stream()
                .filter(schedule -> schedule.getPatient().getId()==id)
                .collect(Collectors.toList());

    }

}

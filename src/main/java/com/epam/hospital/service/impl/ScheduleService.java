package com.epam.hospital.service.impl;

import com.epam.hospital.model.Schedule;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.elements.ScheduleRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ServiceUtils;

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
    public boolean create(Schedule schedule) throws DBException, ValidateException {
        ServiceUtils.dateVisitValidate(Fields.VISIT_TIME,schedule.getDateVisit());

        return scheduleRepository.create(schedule);
    }

    @Override
    public Schedule readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id==null) throw new ValidateException("schedule");
        return scheduleRepository.readByID(id);
    }

    @Override
    public boolean update(Schedule schedule) throws DBException, ValidateException {
        ServiceUtils.dateVisitValidate(Fields.VISIT_TIME,schedule.getDateVisit());
        return scheduleRepository.updateSchedule(schedule);
    }

    @Override
    public void delete(Schedule schedule) throws DBException {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> getAll(int[] limit,String sortRule) throws DBException, SQLException {
        return scheduleRepository.getAllSchedules();

    }

    public List<Schedule> getScheduleByPatientId(int id) throws DBException, SQLException {
        return scheduleRepository.readByPatientID(id);

    }
    public List<Schedule> getScheduleByDoctorId(int id) throws DBException, SQLException {
        return scheduleRepository.readByDoctorID(id);

    }
    public int getSize() throws DBException {
        return scheduleRepository.getSize();
    }

}

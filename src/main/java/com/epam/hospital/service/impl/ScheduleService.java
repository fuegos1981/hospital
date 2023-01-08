package com.epam.hospital.service.impl;

import com.epam.hospital.dto.ScheduleDto;
import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.repository.elements.ScheduleRepository;
import com.epam.hospital.service.MappingUtils;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ServiceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScheduleService implements Service<ScheduleDto> {

    private static ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;
    private final MappingUtils mappingUtils;

    private ScheduleService() {

        this.scheduleRepository = scheduleRepository.getRepository();
        this.mappingUtils = new MappingUtils();
    }

    public static ScheduleService getScheduleService() {
        return Objects.requireNonNullElseGet(scheduleService, ScheduleService::new);
    }

    @Override
    public boolean create(ScheduleDto scheduleDto) throws DBException, ValidateException, SQLException {
        checkSchedule(scheduleDto);
        return scheduleRepository.create(mappingUtils.mapToSchedule(scheduleDto));
    }

    @Override
    public ScheduleDto readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id==null)
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
    public List<ScheduleDto> getAll(Map<String, Integer> selection,SortRule sortRule,int[] limit) throws DBException, SQLException {

        return scheduleRepository.getAllSchedules(selection, sortRule, limit).stream()
                .map(mappingUtils::mapToScheduleDto)
                .collect(Collectors.toList());

    }

    public int getSize(Map<String, Integer> selection) throws DBException {
        return scheduleRepository.getSize(selection);
    }

    private void checkSchedule(ScheduleDto schedule) throws ValidateException {
        if (schedule==null){
            throw new ValidateException("schedule");
        }
        if(schedule.getPatientId()==null){
            throw new ValidateException("patient");
        }
        if(schedule.getDoctorId()==null){
            throw new ValidateException("doctor");
        }
        ServiceUtils.dateVisitValidate(Fields.VISIT_TIME,schedule.getDateVisit());
    }

}

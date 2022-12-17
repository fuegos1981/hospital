package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.GlobalRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository extends GlobalRepository<Schedule> {
    private static ScheduleRepository  scheduleRepository;

    private ScheduleRepository() {
    }

    public static ScheduleRepository getRepository(){
        if (scheduleRepository==null)  {
            scheduleRepository = new ScheduleRepository();
            connectionPool = ConnectionPool.getInstance();
        }
        return scheduleRepository;
    }

    public Schedule readByID(int id) throws DBException {
        return scheduleRepository.read(Constants.GET_SCHEDULE_BY_ID,id);
    }
    public List<Schedule> getAllSchedules() throws DBException {
        return scheduleRepository.findAll(Constants.GET_ALL_SCHEDULE);
    }

    public boolean create(Schedule schedule) throws DBException {

        Object[] objects = {schedule.getDoctor().getId(), schedule.getPatient().getId(), schedule.getDateVisit()};
        return scheduleRepository.insert(Constants.ADD_SCHEDULE,null, objects)>=0;
    }
    public boolean delete(Schedule schedule) throws DBException {
        return scheduleRepository.delete(Constants.DELETE_DOCTOR,null, schedule.getId());
    }

    @Override
    protected Schedule readByResultSet(ResultSet rs) throws SQLException {
        if(rs.next()){
            return getSchedule(rs);
        }
        return null;
    }

    private Schedule getSchedule(ResultSet rs) throws SQLException {
        Doctor doctor = null;
        Patient patient= null;
        try {
            doctor = DoctorRepository.getRepository().readByID(rs.getInt(2));
            patient =PatientRepository.getRepository().readByID(rs.getInt(3));
        } catch (DBException e) {
            e.printStackTrace();
        }
        return Schedule.createSchedule(doctor, patient, rs.getDate(4));
    }


    @Override
    protected List<Schedule> findByResultSet(ResultSet rs) throws SQLException {
        List<Schedule> list = new ArrayList<>();
        while(rs.next()){
            list.add(getSchedule(rs));
        }
        return list;
    }
}

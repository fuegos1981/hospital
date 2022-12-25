package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.Constants;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.GlobalRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleRepository extends GlobalRepository<Schedule> {
    private static ScheduleRepository  scheduleRepository;

    private ScheduleRepository() {
    }

    public static ScheduleRepository getRepository(){
        if (scheduleRepository==null)  {
            scheduleRepository = new ScheduleRepository();
        }
        return new ScheduleRepository();
    }

    public Schedule readByID(int id) throws DBException {
        return scheduleRepository.read(Constants.GET_SCHEDULE_BY_ID,id);
    }

    public boolean updateSchedule(Schedule schedule) throws DBException {
        Object[] objects = {schedule.getDoctor().getId(), schedule.getPatient().getId(), schedule.getDateVisit(),schedule.getId()};
        return scheduleRepository.update(Constants.UPDATE_SCHEDULE, objects);
    }

    public List<Schedule> readByPatientID(int id) throws DBException {
        return scheduleRepository.findAll(Constants.GET_SCHEDULE_BY_PATIENT_ID,id);
    }

    public List<Schedule> readByDoctorID(int id) throws DBException {
        return scheduleRepository.findAll(Constants.GET_SCHEDULE_BY_DOCTOR_ID,id);
    }

    public List<Schedule> getAllSchedules() throws DBException {
        return scheduleRepository.findAll(Constants.GET_ALL_SCHEDULE);
    }
    public List<Schedule> getAllSchedules(Map<String, Integer> selection) throws DBException {
        return scheduleRepository.findAll(Constants.GET_ALL_SCHEDULE+RepositoryUtils.getSelectionString(selection),
                selection.values().toArray());
    }
    public int getSize() throws DBException {
        return scheduleRepository.readSize(Constants.GET_SIZE_SCHEDULE);
    }
    public boolean create(Schedule schedule) throws DBException {

        Object[] objects = {schedule.getDoctor().getId(), schedule.getPatient().getId(), schedule.getDateVisit()};
        return scheduleRepository.insert(Constants.ADD_SCHEDULE,objects)>=0;
    }
    public boolean delete(Schedule schedule) throws DBException {
        return scheduleRepository.delete(Constants.DELETE_SCHEDULE,schedule.getId());
    }

    @Override
    protected Schedule readByResultSet(ResultSet rs) throws SQLException, DBException {
        if(rs.next()){
            return getSchedule(rs);
        }
        return null;
    }

    private Schedule getSchedule(ResultSet rs) throws SQLException, DBException {
        Doctor doctor = DoctorRepository.getRepository().readByID(rs.getInt(Fields.DOCTOR_ID));
        Patient patient =PatientRepository.getRepository().readByID(rs.getInt(Fields.PATIENT_ID));
        Schedule schedule=Schedule.createSchedule(doctor, patient, rs.getTimestamp(Fields.VISIT_TIME));
        schedule.setId(rs.getInt(Fields.ID));
        return schedule;
    }


    @Override
    protected List<Schedule> findByResultSet(ResultSet rs) throws SQLException, DBException {
        List<Schedule> list = new ArrayList<>();
        while(rs.next()){
            list.add(getSchedule(rs));
        }
        return list;
    }
}

package com.hospital.app.repository.elements;

import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Patient;
import com.hospital.app.model.Schedule;
import com.hospital.app.repository.Constants;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.GlobalRepository;
import com.hospital.app.repository.QueryRedactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository extends GlobalRepository<Schedule> {
    private static ScheduleRepository scheduleRepository;

    private ScheduleRepository() {
    }

    public static ScheduleRepository getRepository() {
        if (scheduleRepository == null) {
            scheduleRepository = new ScheduleRepository();
        }
        return new ScheduleRepository();
    }

    public Schedule readByID(int id) throws DBException {
        return scheduleRepository.read(Constants.GET_SCHEDULE_BY_ID, id);
    }

    public boolean create(Schedule schedule) throws DBException {

        Object[] objects = {schedule.getDoctor().getId(), schedule.getPatient().getId(), schedule.getDateVisit()};
        return scheduleRepository.insert(Constants.ADD_SCHEDULE, objects) >= 0;
    }

    public boolean delete(Schedule schedule) throws DBException {
        return scheduleRepository.delete(Constants.DELETE_SCHEDULE, schedule.getId());
    }

    public boolean updateSchedule(Schedule schedule) throws DBException {
        Object[] objects = {schedule.getDoctor().getId(), schedule.getPatient().getId(), schedule.getDateVisit(), schedule.getId()};
        return scheduleRepository.update(Constants.UPDATE_SCHEDULE, objects);
    }

    public List<Schedule> getAllSchedules(QueryRedactor qr) throws DBException {
        return scheduleRepository.findAll(qr.getQuery(Constants.GET_ALL_SCHEDULE),
                qr.getSelectionValues());
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return scheduleRepository.readSize(qr.getQuery(Constants.GET_SIZE_SCHEDULE),
                qr.getSelectionValues());
    }

    public List<Schedule> getAllSchedules() throws DBException {
        return scheduleRepository.findAll(Constants.GET_ALL_SCHEDULE);

    }

    public int getSize() throws DBException {
        return scheduleRepository.readSize(Constants.GET_SIZE_SCHEDULE);
    }

    @Override
    protected Schedule readByResultSet(ResultSet rs) throws SQLException, DBException {
        if (rs.next()) {
            return getSchedule(rs);
        }
        return null;
    }

    @Override
    protected List<Schedule> findByResultSet(ResultSet rs) throws SQLException, DBException {
        List<Schedule> list = new ArrayList<>();
        while (rs.next()) {
            list.add(getSchedule(rs));
        }
        return list;
    }

    private Schedule getSchedule(ResultSet rs) throws SQLException, DBException {
        Doctor doctor = DoctorRepository.getRepository().readByID(rs.getInt(Fields.DOCTOR_ID));
        doctor.setId(rs.getInt(Fields.DOCTOR_ID));
        Patient patient = PatientRepository.getRepository().readByID(rs.getInt(Fields.PATIENT_ID));
        patient.setId(rs.getInt(Fields.PATIENT_ID));
        Schedule schedule = Schedule.createSchedule(doctor, patient, rs.getTimestamp(Fields.VISIT_TIME));
        schedule.setId(rs.getInt(Fields.ID));
        return schedule;
    }


}

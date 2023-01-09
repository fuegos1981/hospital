package com.epam.hospital.repository.elements;

import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.model.*;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DoctorRepository extends GlobalRepository<Doctor> {
    private static DoctorRepository  doctorRepository;

    private DoctorRepository() {
    }

    public static DoctorRepository getRepository(){
        if (doctorRepository==null)  {
            doctorRepository = new DoctorRepository();
        }
        return doctorRepository;
    }

    public Doctor readByID(int id) throws DBException, SQLException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_ID,id);
    }
    public Doctor readByLogin(String login) throws DBException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_LOGIN,login);
    }

    public boolean create(Doctor doctor) throws DBException {

        Object[] objects = {doctor.getLastName(), doctor.getFirstName(), doctor.getCategory().getId(),
                doctor.getLogin(), doctor.getPassword(), Role.getID(doctor.getRole())};
            int idDoctor = doctorRepository.insert(Constants.ADD_DOCTOR,objects);
            return idDoctor >=0;
    }

    public boolean updateDoctor(Doctor doctor) throws DBException {

        Object[] objects = {doctor.getLastName(), doctor.getFirstName(), doctor.getCategory().getId(),
                doctor.getLogin(), doctor.getPassword(), Role.getID(doctor.getRole()),doctor.getId()};
        return doctorRepository.update(Constants.UPDATE_DOCTOR, objects);
    }

    public boolean delete(Doctor doctor) throws DBException {
        return doctorRepository.delete(Constants.DELETE_DOCTOR,doctor.getId());
    }

    public List<Doctor> getAllDoctors(Map<String, Object> selection, SortRule sortRule, int[] limit) throws DBException {
        if (selection==null)
            return doctorRepository.findAll(QueryRedactor.getRedactor(Constants.GET_ALL_DOCTORS,null,sortRule,limit).getQuery());
        else
            return doctorRepository.findAll(QueryRedactor.getRedactor(Constants.GET_ALL_DOCTORS,selection,sortRule,limit).getQuery(),
                selection.values().toArray());
    }

    public int getSize(Map<String, Object> selection) throws DBException {
        if (selection==null)
            return doctorRepository.readSize(Constants.GET_SIZE_DOCTOR);
        else
            return doctorRepository.readSize(QueryRedactor.getRedactor(Constants.GET_SIZE_DOCTOR,selection).getQuery(),
                    selection.values().toArray());
    }

    @Override
    protected Doctor readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            return getDoctor(rs);
        }
        return null;
    }

    @Override
    protected List<Doctor> findByResultSet(ResultSet rs) throws SQLException {
        List<Doctor> list = new ArrayList<>();
        while(rs.next()){
            list.add(getDoctor(rs));
        }
        return list;
    }

    private Doctor getDoctor(ResultSet rs) throws SQLException {

        Doctor doctor = Doctor.createDoctor(rs.getString(Fields.LAST_NAME),
                rs.getString(Fields.FIRST_NAME),
                Category.createInstance(rs.getString(Fields.CATEGORY_NAME)));
        doctor.setPassword(rs.getString(Fields.PASSWORD));
        doctor.setLogin(rs.getString(Fields.LOGIN));
        doctor.setRole(Role.valueOf(rs.getString(Fields.ROLE_NAME).toUpperCase()));
        doctor.setId(rs.getInt(Fields.ID));
        return doctor;
    }


}

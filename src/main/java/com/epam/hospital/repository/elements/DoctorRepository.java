package com.epam.hospital.repository.elements;

import com.epam.hospital.model.*;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository extends GlobalRepository<Doctor> {
    private static DoctorRepository  doctorRepository;

    private DoctorRepository() {
    }

    public static DoctorRepository getRepository(){
        if (doctorRepository==null)  {
            doctorRepository = new DoctorRepository();
            connectionPool = ConnectionPool.getInstance();
        }
        return doctorRepository;
    }

    public Doctor readByID(int id) throws DBException, SQLException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_ID,id);
    }
    public List<Doctor> getAllDoctors() throws DBException {
        return doctorRepository.findAll(Constants.GET_ALL_DOCTORS);
    }

    public boolean create(Doctor doctor) throws DBException {
        Person person = doctor.getPerson();
        Object[] objects = {person.getLastName(), person.getFirstName(), person.getBirthday(), person.getEmail(), Gender.getID(person.getGender())};
        int idPerson = doctorRepository.insert(Constants.ADD_PERSON,null, objects);
        if (idPerson>=0){
            person.setId(idPerson);
            int idDoctor = doctorRepository.insert(Constants.ADD_DOCTOR,null, idPerson,doctor.getCategory().getId());
            return idDoctor >=0;
        }
        return false;
    }
    public boolean delete(Doctor doctor) throws DBException {
        return doctorRepository.delete(Constants.DELETE_DOCTOR,null, doctor.getId());
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
        Person person = RepositoryUtils.getPerson(rs);
        Doctor doctor = Doctor.createDoctor(person, Category.createInstance(rs.getString(Fields.CATEGORY_NAME)));
        doctor.setId(rs.getInt(Fields.ID));
        return doctor;
    }
}

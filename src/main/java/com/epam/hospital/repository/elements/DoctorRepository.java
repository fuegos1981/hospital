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
    public List<Doctor> getAllDoctors() throws DBException, SQLException {
        return doctorRepository.findAll(Constants.GET_ALL_DOCTORS);
    }

    public boolean create(Doctor doctor) throws DBException {
        Person person = doctor.getPerson();
        Object[] objects = {person.getLastName(), person.getFirstName(), person.getBirthday(), person.getGender().toString()};
        int idPerson = doctorRepository.insert(Constants.ADD_PERSON, objects);
        if (idPerson>=0){
            person.setId(idPerson);
            int idDoctor = doctorRepository.insert(Constants.ADD_DOCTOR, idPerson,doctor.getCategory().getId());
            return idDoctor >=0;
        }
        return false;
    }
    public boolean delete(Doctor doctor) throws DBException {
        return doctorRepository.delete(Constants.DELETE_DOCTOR, doctor.getId());
    }

    @Override
    protected Doctor readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            Person person = RepositoryUtils.getPerson(rs);
            Doctor doctor = Doctor.createDoctor(person,Category.createInstance(rs.getString(4)));
            doctor.setId(rs.getInt(2));
            return doctor;
        }
        return null;
    }

    @Override
    protected List<Doctor> findByResultSet(ResultSet rs) throws SQLException {
        List<Doctor> list = new ArrayList<>();
        while(rs.next()){
            Person person = RepositoryUtils.getPerson(rs);
            Doctor doctor = Doctor.createDoctor(person, Category.createInstance(rs.getString(7)));
            doctor.setId(rs.getInt(1));
            list.add(doctor);
        }
        return list;
    }
}

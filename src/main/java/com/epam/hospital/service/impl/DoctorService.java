package com.epam.hospital.service.impl;

import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.DoctorRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DoctorService implements Service<Doctor> {
    private static DoctorService doctorService;
    private DoctorRepository doctorRepository;
    private DoctorService() {
        this.doctorRepository = doctorRepository.getRepository();
    }
    public static DoctorService getDoctorService(){
        return Objects.requireNonNullElseGet(doctorService, DoctorService::new);
    }
    @Override
    public boolean create(Doctor doctor) throws DBException {
        return doctorRepository.create(doctor);
    }

    @Override
    public Doctor readById(int id) throws DBException, SQLException {
        return  doctorRepository.readByID(id);
    }

    @Override
    public boolean update(Doctor doctor) throws DBException {
        return doctorRepository.create(doctor);
    }

    @Override
    public void delete(Doctor doctor) throws DBException {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> getAll() throws DBException, SQLException {
        return doctorRepository.getAllDoctors();
    }
}

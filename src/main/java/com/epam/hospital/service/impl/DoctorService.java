package com.epam.hospital.service.impl;

import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.DoctorRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.Comparator;
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
    public List<Doctor> getAll(String sortRule) throws DBException, SQLException {
        return sort(doctorRepository.getAllDoctors(),sortRule);

    }
    public List<Doctor> sort(List<Doctor> list, String sortRule){
        if (sortRule != null && !sortRule.equals(" ")){
            String[] s = sortRule.split(" ");
            Comparator<Doctor> comp;
            if (s[0].equals("name"))
                comp = Comparator.comparing(e -> e.getPerson().toString());
            else
                comp = Comparator.comparing(e -> e.getCategory().toString());
            if (s[1].equals("desc"))
                comp =comp.reversed();
            list.sort(comp);
        }
        return list;

    }
}

package com.epam.hospital.service.impl;

import com.epam.hospital.model.Doctor;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.elements.DoctorRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ServiceUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DoctorService implements Service<Doctor> {
    private static DoctorService doctorService;
    private static DoctorRepository doctorRepository;
    private DoctorService() {
        this.doctorRepository = doctorRepository.getRepository();
    }
    public static DoctorService getDoctorService(){
        return Objects.requireNonNullElseGet(doctorService, DoctorService::new);
    }
    @Override
    public boolean create(Doctor doctor) throws DBException, ValidateException {
        checkDoctor(doctor);
        doctor.setPassword(DigestUtils.md5Hex(doctor.getPassword()));
        return doctorRepository.create(doctor);
    }

    @Override
    public Doctor readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null) {
            throw new ValidateException("doctor");
        }
        else
            return  doctorRepository.readByID(id);
    }
    public Doctor readByLoginPassword(String login, String pass) throws DBException, ValidateException {
        Doctor doctor = doctorRepository.readByLogin(login);

        if (doctor == null || !doctor.getPassword().equals(DigestUtils.md5Hex(pass))) {
            throw  new ValidateException("error_log_pass");
        }
        return doctor;
    }
    public Doctor readByLogin(String login) throws DBException, ValidateException {
        Doctor doctor = doctorRepository.readByLogin(login);
        if (doctor == null) {
            throw  new ValidateException("login");
        }
        return doctor;
    }
    @Override
    public boolean update(Doctor doctor) throws DBException, ValidateException {
        checkDoctor(doctor);
        doctor.setPassword(DigestUtils.md5Hex(doctor.getPassword()));
        return doctorRepository.create(doctor);
    }

    @Override
    public void delete(Doctor doctor) throws DBException {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> getAll(int[] limit, String sortRule) throws DBException, SQLException {
        return doctorRepository.getAllDoctors(limit,sortRule);
    }
    public int getSize() throws DBException {
        return doctorRepository.getSize();
    }

    private void checkDoctor(Doctor doctor) throws ValidateException, DBException {
        ServiceUtils.nameValidate(Fields.LAST_NAME,doctor.getLastName());
        ServiceUtils.nameValidate(Fields.FIRST_NAME,doctor.getFirstName());
        if (doctor.getLogin()==null||doctor.getLogin().isEmpty()||doctorService.readByLogin(doctor.getLogin())!=null){
            throw new ValidateException("login");
        }
    }
}

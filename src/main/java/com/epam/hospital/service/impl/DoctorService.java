package com.epam.hospital.service.impl;

import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.Doctor;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.repository.elements.DoctorRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ValidatorUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DoctorService implements Service<Doctor> {
    private static DoctorRepository doctorRepository;
    private DoctorService() {
        doctorRepository = DoctorRepository.getRepository();
    }
    public static DoctorService getDoctorService(){
        return new DoctorService();
    }

    @Override
    public Doctor readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null) {
            return null;
        }
        else{
            return  doctorRepository.readByID(id);
        }

    }
    public Doctor readByLoginPassword(String login, String pass) throws DBException, ValidateException {
        Doctor doctor = doctorRepository.readByLogin(login);

        if (doctor == null || !doctor.getPassword().equals(DigestUtils.md5Hex(pass))) {
            throw  new ValidateException("error_log_pass");
        }
        return doctor;
    }
    public Doctor readByLogin(String login) throws DBException{
        if (login == null) {
           return null;
        }
        return doctorRepository.readByLogin(login);
    }

    @Override
    public boolean create(Doctor doctor) throws DBException, ValidateException {
        checkDoctor(doctor);
        doctor.setPassword(DigestUtils.md5Hex(doctor.getPassword()));
        return doctorRepository.create(doctor);
    }

    @Override
    public boolean update(Doctor doctor) throws DBException, ValidateException, SQLException {
        checkDoctor(doctor);
        if (doctor.getPassword().equals(Fields.PASSWORD_NOT_CHANGE)){
            doctor.setPassword(doctorRepository.readByID(doctor.getId()).getPassword());
        }
        else
            doctor.setPassword(DigestUtils.md5Hex(doctor.getPassword()));
        return doctorRepository.updateDoctor(doctor);
    }

    @Override
    public void delete(Doctor doctor) throws DBException {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> getAll(Map<String, Object> selection, SortRule sortRule, int[] limit) throws DBException, SQLException {
        return doctorRepository.getAllDoctors(selection, sortRule, limit);
    }
    public int getSize(Map<String,Object> selection) throws DBException {
        return doctorRepository.getSize(selection);
    }

    private void checkDoctor(Doctor doctor) throws ValidateException, DBException {
        ValidatorUtils.nameValidate(Fields.LAST_NAME,doctor.getLastName());
        ValidatorUtils.nameValidate(Fields.FIRST_NAME,doctor.getFirstName());
        if (doctor.getLogin()==null||(doctor.getLogin().isEmpty())){
            throw new ValidateException("login");
        }
        Doctor doctorDop = readByLogin(doctor.getLogin());
        if(doctorDop!=null && doctorDop.getId()!=doctor.getId()){
            throw new ValidateException("login_exist");
        }
        if (doctor.getPassword()==null||doctor.getPassword().length()<3){
            throw new ValidateException("password");
        }
        if (doctor.getCategory()==null){
            throw new ValidateException("category");
        }

    }
}

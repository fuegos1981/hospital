package com.hospital.app.service.impl;

import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Doctor;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.elements.DoctorRepository;
import com.hospital.app.service.Service;
import com.hospital.app.service.ValidatorUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.List;

public class DoctorService implements Service<Doctor> {
    private static DoctorRepository doctorRepository;

    private DoctorService() {
        doctorRepository = DoctorRepository.getRepository();
    }

    public static DoctorService getDoctorService() {
        return new DoctorService();
    }

    @Override
    public Doctor readById(Integer id) throws DBException, SQLException, ValidateException {
        if (id == null) {
            return null;
        } else {
            return doctorRepository.readByID(id);
        }

    }

    public Doctor readByLoginPassword(String login, String pass) throws DBException, ValidateException {
        Doctor doctor = doctorRepository.readByLogin(login);

        if (doctor == null || !doctor.getPassword().equals(DigestUtils.md5Hex(pass))) {
            throw new ValidateException("error_log_pass");
        }
        return doctor;
    }

    public Doctor readByLogin(String login) throws DBException {
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
        if (doctor.getPassword().equals(Fields.PASSWORD_NOT_CHANGE)) {
            doctor.setPassword(doctorRepository.readByID(doctor.getId()).getPassword());
        } else
            doctor.setPassword(DigestUtils.md5Hex(doctor.getPassword()));
        return doctorRepository.updateDoctor(doctor);
    }

    @Override
    public void delete(Doctor doctor) throws DBException {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<Doctor> getAll(QueryRedactor qr) throws DBException, SQLException {
        return doctorRepository.getAllDoctors(qr);
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return doctorRepository.getSize(qr);
    }

    @Override
    public List<Doctor> getAll() throws DBException, SQLException {
        return doctorRepository.getAllDoctors();
    }

    public int getSize() throws DBException {
        return doctorRepository.getSize();
    }

    private void checkDoctor(Doctor doctor) throws ValidateException, DBException {
        ValidatorUtils.nameValidate(Fields.LAST_NAME, doctor.getLastName());
        ValidatorUtils.nameValidate(Fields.FIRST_NAME, doctor.getFirstName());
        if (doctor.getLogin() == null || (doctor.getLogin().isEmpty())) {
            throw new ValidateException("login");
        }
        Doctor doctorDop = readByLogin(doctor.getLogin());
        if (doctorDop != null && doctorDop.getId() != doctor.getId()) {
            throw new ValidateException("login_exist");
        }
        if (doctor.getPassword() == null || doctor.getPassword().length() < 3) {
            throw new ValidateException("password");
        }
        if (doctor.getCategory() == null) {
            throw new ValidateException("category");
        }

    }
}

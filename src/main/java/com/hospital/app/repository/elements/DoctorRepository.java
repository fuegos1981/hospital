package com.hospital.app.repository.elements;

import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.Category;
import com.hospital.app.model.Doctor;
import com.hospital.app.model.Role;
import com.hospital.app.model.SimpleModel;
import com.hospital.app.repository.Constants;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.GlobalRepository;
import com.hospital.app.repository.QueryRedactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository extends GlobalRepository<Doctor> {
    private static DoctorRepository doctorRepository;

    private DoctorRepository() {
    }

    public static DoctorRepository getRepository() {
        if (doctorRepository == null) {
            doctorRepository = new DoctorRepository();
        }
        return doctorRepository;
    }

    public Doctor readByID(int id) throws DBException, SQLException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_ID, id);
    }

    public Doctor readByLogin(String login) throws DBException {
        return doctorRepository.read(Constants.GET_DOCTOR_BY_LOGIN, login);
    }

    public boolean create(Doctor doctor) throws DBException {
        Object[] objects = {doctor.getLastName(), doctor.getFirstName(), doctor.getCategory().getId(),
                doctor.getLogin(), doctor.getPassword(), Role.getID(doctor.getRole())};
        int idDoctor = doctorRepository.insert(Constants.ADD_DOCTOR, objects);
        return idDoctor >= 0;
    }

    public boolean updateDoctor(Doctor doctor) throws DBException {
        Object[] objects = {doctor.getLastName(), doctor.getFirstName(), doctor.getCategory().getId(),
                doctor.getLogin(), doctor.getPassword(), Role.getID(doctor.getRole()), doctor.getId()};
        return doctorRepository.update(Constants.UPDATE_DOCTOR, objects);
    }

    public boolean delete(Doctor doctor) throws DBException {
        return doctorRepository.delete(Constants.DELETE_DOCTOR, doctor.getId());
    }

    public List<Doctor> getAllDoctors(QueryRedactor qr) throws DBException {
        return doctorRepository.findAll(qr.getQuery(Constants.GET_ALL_DOCTORS),
                qr.getSelectionValues());
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return doctorRepository.readSize(qr.getQuery(Constants.GET_SIZE_DOCTOR),
                qr.getSelectionValues());
    }

    public List<Doctor> getAllDoctors() throws DBException {
        return doctorRepository.findAll(Constants.GET_ALL_DOCTORS);
    }

    public int getSize() throws DBException {
        return doctorRepository.readSize(Constants.GET_SIZE_DOCTOR);
    }

    @Override
    protected Doctor readByResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return getDoctor(rs);
        }
        return null;
    }

    @Override
    protected List<Doctor> findByResultSet(ResultSet rs) throws SQLException {
        List<Doctor> list = new ArrayList<>();
        while (rs.next()) {
            list.add(getDoctor(rs));
        }
        return list;
    }

    private Doctor getDoctor(ResultSet rs) throws SQLException {
        Category category = (Category) SimpleModel.getSimpleInstance(Constants.CATEGORY);
        category.setName(rs.getString(Fields.CATEGORY_NAME));
        Doctor doctor = Doctor.createDoctor(rs.getString(Fields.LAST_NAME),
                rs.getString(Fields.FIRST_NAME),
                category);
        doctor.setPassword(rs.getString(Fields.PASSWORD));
        doctor.setLogin(rs.getString(Fields.LOGIN));
        doctor.setRole(Role.valueOf(rs.getString(Fields.ROLE_NAME).toUpperCase()));
        doctor.setId(rs.getInt(Fields.ID));
        return doctor;
    }


}

package com.hospital.app.repository.elements;

import com.hospital.app.exceptions.DBException;
import com.hospital.app.model.Gender;
import com.hospital.app.model.Patient;
import com.hospital.app.repository.Constants;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.GlobalRepository;
import com.hospital.app.repository.QueryRedactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository extends GlobalRepository<Patient> {

    private static PatientRepository patientRepository;

    private PatientRepository() {
    }

    public static PatientRepository getRepository() {
        if (patientRepository == null) {
            patientRepository = new PatientRepository();
        }
        return patientRepository;
    }

    public Patient readByID(int id) throws DBException, SQLException {
        return patientRepository.read(Constants.GET_Patient_BY_ID, id);
    }

    public boolean create(Patient patient) throws DBException {

        Object[] objects = {patient.getLastName(), patient.getFirstName(), patient.getBirthday(),
                patient.getEmail(), Gender.getID(patient.getGender())};
        int idPatient = patientRepository.insert(Constants.ADD_PATIENT, objects);
        return idPatient >= 0;

    }

    public boolean updatePatient(Patient patient) throws DBException {

        Object[] objects = {patient.getLastName(), patient.getFirstName(), patient.getBirthday(), patient.getEmail(),
                Gender.getID(patient.getGender()), patient.getId()};
        return patientRepository.update(Constants.UPDATE_PATIENT, objects);
    }

    public boolean delete(Patient patient) throws DBException {
        return patientRepository.delete(Constants.DELETE_PATIENT, patient.getId());
    }

    public List<Patient> getAllPatients(QueryRedactor qr) throws DBException {
        return patientRepository.findAll(qr.getQuery(Constants.GET_ALL_PATIENTS),
                qr.getSelectionValues());
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return patientRepository.readSize(qr.getQuery(Constants.GET_SIZE_PATIENT),
                qr.getSelectionValues());
    }

    public List<Patient> getAllPatients() throws DBException {
        return patientRepository.findAll(Constants.GET_ALL_PATIENTS);
    }

    public int getSize() throws DBException {
        return patientRepository.readSize(Constants.GET_SIZE_PATIENT);
    }

    @Override
    protected Patient readByResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return getPatient(rs);
        }
        return null;
    }

    @Override
    protected List<Patient> findByResultSet(ResultSet rs) throws SQLException {
        List<Patient> list = new ArrayList<>();
        while (rs.next()) {
            list.add(getPatient(rs));
        }
        return list;
    }

    public static Patient getPatient(ResultSet rs) throws SQLException {
        Patient patient = Patient.createPatient(
                rs.getString(Fields.LAST_NAME),
                rs.getString(Fields.FIRST_NAME),
                rs.getDate(Fields.PATIENT_BIRTHDAY),
                rs.getString(Fields.PATIENT_EMAIL),
                Gender.valueOf(rs.getString(Fields.PATIENT_GENDER).toUpperCase()));
        patient.setId(rs.getInt(Fields.ID));
        return patient;
    }
}

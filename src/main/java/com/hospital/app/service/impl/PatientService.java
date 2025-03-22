package com.hospital.app.service.impl;

import com.hospital.app.exceptions.ValidateException;
import com.hospital.app.model.Patient;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Fields;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.repository.elements.PatientRepository;
import com.hospital.app.service.Service;
import com.hospital.app.service.ValidatorUtils;

import java.sql.SQLException;
import java.util.List;

public class PatientService implements Service<Patient> {

    private PatientRepository patientRepository;

    private PatientService() {
        this.patientRepository = PatientRepository.getRepository();
    }

    public static PatientService getPatientService() {
        return new PatientService();
    }

    @Override
    public Patient readById(Integer id) throws DBException, SQLException {
        if (id == null)
            return null;
        else
            return patientRepository.readByID(id);
    }

    @Override
    public boolean create(Patient patient) throws DBException, ValidateException {
        checkPatient(patient);
        return patientRepository.create(patient);
    }

    @Override
    public boolean update(Patient patient) throws DBException, ValidateException {
        checkPatient(patient);
        return patientRepository.updatePatient(patient);
    }

    @Override
    public void delete(Patient patient) throws DBException {
        patientRepository.delete(patient);
    }

    @Override
    public List<Patient> getAll(QueryRedactor qr) throws DBException, SQLException {
        return patientRepository.getAllPatients(qr);
    }

    public int getSize(QueryRedactor qr) throws DBException {
        return patientRepository.getSize(qr);
    }

    @Override
    public List<Patient> getAll() throws DBException, SQLException {
        return patientRepository.getAllPatients();
    }

    public int getSize() throws DBException {
        return patientRepository.getSize();
    }

    private void checkPatient(Patient patient) throws ValidateException {
        if (patient == null) {
            throw new ValidateException("patient");
        }
        ValidatorUtils.nameValidate(Fields.LAST_NAME, patient.getLastName());
        ValidatorUtils.nameValidate(Fields.FIRST_NAME, patient.getFirstName());
        ValidatorUtils.emailValidate("Email", patient.getEmail());
        ValidatorUtils.birthdayValidate(Fields.PATIENT_BIRTHDAY, patient.getBirthday());
    }

}

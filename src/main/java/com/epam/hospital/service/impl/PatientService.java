package com.epam.hospital.service.impl;

import com.epam.hospital.exceptions.ValidateException;
import com.epam.hospital.model.Patient;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.repository.elements.PatientRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ValidatorUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PatientService implements Service<Patient> {

    private PatientRepository patientRepository;

    private PatientService() {
        this.patientRepository = PatientRepository.getRepository();
    }

    public static PatientService getPatientService(){
        return new PatientService();
    }

    @Override
    public Patient readById(Integer id) throws DBException, SQLException{
        if(id==null)
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
    public List<Patient> getAll(Map<String, Object> selection, SortRule sortRule, int[] limit) throws DBException, SQLException {
        return patientRepository.getAllPatients(selection, sortRule, limit);
    }

    public int getSize(Map<String, Object> selection) throws DBException {
        return patientRepository.getSize(selection);
    }

    private void checkPatient(Patient patient) throws ValidateException{
        if (patient==null) {
            throw new ValidateException("patient");
        }
        ValidatorUtils.nameValidate(Fields.LAST_NAME,patient.getLastName());
        ValidatorUtils.nameValidate(Fields.FIRST_NAME,patient.getFirstName());
        ValidatorUtils.emailValidate("Email",patient.getEmail());
        ValidatorUtils.birthdayValidate(Fields.PATIENT_BIRTHDAY,patient.getBirthday());
    }

}

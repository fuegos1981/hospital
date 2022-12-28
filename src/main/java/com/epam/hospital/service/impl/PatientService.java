package com.epam.hospital.service.impl;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.Fields;
import com.epam.hospital.repository.elements.PatientRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ServiceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class PatientService implements Service<Patient> {
    private static PatientService patientService;
    private PatientRepository patientRepository;

    private PatientService() {
        this.patientRepository = patientRepository.getRepository();
    }
    public static PatientService getPatientService(){
        return Objects.requireNonNullElseGet(patientService, PatientService::new);
    }
    @Override
    public boolean create(Patient patient) throws DBException, ValidateException {
        checkPatient(patient);
        return patientRepository.create(patient);
    }

    @Override
    public Patient readById(Integer id) throws DBException, SQLException, ValidateException {
        if(id==null)
            throw new ValidateException("patient");
        else
            return patientRepository.readByID(id);
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
    public List<Patient> getAll(int[] limit,String sortRule) throws DBException, SQLException {
        return patientRepository.getAllPatients(limit, sortRule);
    }

    public int getSize() throws DBException {
        return patientRepository.getSize();
    }

    private void checkPatient(Patient patient) throws ValidateException{
        if (patient==null) {
            throw new ValidateException("patient");
        }
        ServiceUtils.nameValidate(Fields.LAST_NAME,patient.getLastName());
        ServiceUtils.nameValidate(Fields.FIRST_NAME,patient.getFirstName());
        ServiceUtils.birthdayValidate(Fields.PATIENT_BIRTHDAY,patient.getBirthday());
    }

}

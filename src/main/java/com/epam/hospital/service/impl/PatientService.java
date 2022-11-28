package com.epam.hospital.service.impl;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.PatientRepository;
import com.epam.hospital.service.Service;

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
    public boolean create(Patient patient) throws DBException {
        return patientRepository.create(patient);
    }

    @Override
    public Patient readById(int id) throws DBException, SQLException {
        return patientRepository.readByID(id);
    }

    @Override
    public boolean update(Patient patient) throws DBException {
        return patientRepository.create(patient);
    }

    @Override
    public void delete(Patient patient) throws DBException {
        patientRepository.delete(patient);
    }

    @Override
    public List getAll() throws DBException, SQLException {
        return patientRepository.getAllPatients();
    }
}

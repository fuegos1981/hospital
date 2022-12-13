package com.epam.hospital.service.impl;

import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.elements.PatientRepository;
import com.epam.hospital.service.Service;
import com.epam.hospital.service.ServiceConstants;

import java.sql.SQLException;
import java.util.Comparator;
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
        if (patient.getPerson().getLastName().matches("")){
            throw new ValidateException("invalid");
        }
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
    public List<Patient> getAll(String sortRule) throws DBException, SQLException {
        return sort(patientRepository.getAllPatients(),sortRule);
    }

    public List<Patient> sort(List<Patient> list, String sortRule) {

        if (sortRule == null || sortRule.equals(" ")) {
            sortRule = ServiceConstants.NAME_ASC;
        }
        String[] s = sortRule.split(" ");
        Comparator<Patient> comp;
        if (s[0].equals(ServiceConstants.NAME))
            comp = Comparator.comparing(e -> e.getPerson().toString());
        else
            comp = Comparator.comparing(e -> e.getPerson().getBirthday());
        if (s[1].equals(ServiceConstants.DESC))
            comp = comp.reversed();
        list.sort(comp);

        return list;
    }
}

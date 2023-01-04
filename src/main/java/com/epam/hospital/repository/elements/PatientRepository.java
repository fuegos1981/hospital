package com.epam.hospital.repository.elements;

import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PatientRepository extends GlobalRepository<Patient>{

    private static PatientRepository  patientRepository;

    private PatientRepository() {
    }

    public static PatientRepository getRepository(){
        if (patientRepository==null)  {
            patientRepository = new PatientRepository();
        }
        return patientRepository;
    }

    public Patient readByID(int id) throws DBException, SQLException {
        return patientRepository.read(Constants.GET_Patient_BY_ID,id);
    }

    public boolean create(Patient patient) throws DBException {

        Object[] objects = {patient.getLastName(), patient.getFirstName(), patient.getBirthday(),
                patient.getEmail(), Gender.getID(patient.getGender())};
        int idPatient = patientRepository.insert(Constants.ADD_PATIENT, objects);
        return idPatient >=0;

    }

    public boolean updatePatient(Patient patient) throws DBException {

        Object[] objects = {patient.getLastName(), patient.getFirstName(), patient.getBirthday(), patient.getEmail(),
                Gender.getID(patient.getGender()),patient.getId()};
        return patientRepository.update(Constants.UPDATE_PATIENT, objects);
    }

    public boolean delete(Patient patient) throws DBException {
        return patientRepository.delete(Constants.DELETE_PATIENT, patient.getId());
    }

    public List<Patient> getAllPatients(Map<String, Integer> selection, SortRule sortRule, int[] limit) throws DBException{
        if (selection==null)
            return patientRepository.findAll(QueryRedactor.getRedactor(Constants.GET_ALL_PATIENTS,null, sortRule,limit).getQuery());
        else
            return patientRepository.findAll(QueryRedactor.getRedactor(Constants.GET_ALL_PATIENTS,selection, sortRule,limit).getQuery(),
                selection.values().toArray());
    }

    public int getSize(Map<String, Integer> selection) throws DBException {
        if (selection==null)
            return patientRepository.readSize(Constants.GET_SIZE_PATIENT);
        else
            return patientRepository.readSize(QueryRedactor.getRedactor(Constants.GET_SIZE_PATIENT,selection).getQuery(),
                selection.values().toArray());
    }

    @Override
    protected Patient readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            return getPatient(rs);
        }
        return null;
    }

    @Override
    protected List<Patient> findByResultSet(ResultSet rs) throws SQLException {
        List<Patient> list = new ArrayList<>();
        while(rs.next()){
            list.add(getPatient(rs));
        }
        return list;
    }

    public static  Patient getPatient(ResultSet rs) throws SQLException {
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

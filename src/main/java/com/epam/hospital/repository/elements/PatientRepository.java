package com.epam.hospital.repository.elements;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
    public List<Patient> getAllPatients(int[] limit,String sortRule) throws DBException{
        return patientRepository.findAll(Constants.GET_ALL_PATIENTS+getSortRule(sortRule)+(limit==null?"":" limit " +limit[0]+","+limit[1]));
    }

    public int getSize() throws DBException {
        return patientRepository.readSize(Constants.GET_SIZE_PATIENT);
    }

    public boolean create(Patient patient) throws DBException {

        Object[] objects = {patient.getLastName(), patient.getFirstName(), patient.getBirthday(), patient.getEmail(), Gender.getID(patient.getGender())};
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

    private String getSortRule(String sortRule) {
        if (sortRule == null || sortRule.equals("name asc"))
            return " order by last_Name,first_Name";
        else if (sortRule.equals("name desc"))
            return " order by last_Name desc,first_Name desc";
        else if (sortRule.equals("birthday asc"))
            return " order by birthday,last_Name,first_Name";
        else if (sortRule.equals("birthday desc"))
            return " order by birthday desc,last_Name,first_Name";
        else return "";
    }
}

package com.epam.hospital.repository.elements;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.Person;
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
            connectionPool = ConnectionPool.getInstance();
        }
        return patientRepository;
    }

    public Patient readByID(int id) throws DBException, SQLException {
        return patientRepository.read(Constants.GET_Patient_BY_ID,id);
    }
    public List<Patient> getAllPatients() throws DBException, SQLException {
        return patientRepository.findAll(Constants.GET_ALL_PATIENTS);
    }

    private Person getPerson(ResultSet rs) throws SQLException {
        Person person = Person.createPerson(rs.getString(3),
                rs.getString(4),
                rs.getDate(5),
                rs.getString(6),
                Gender.valueOf(rs.getString(6).toUpperCase()));
        person.setId(rs.getInt(2));
        return person;
    }

    public boolean create(Patient patient) throws DBException {
        Person person = patient.getPerson();
        Object[] objects = {person.getLastName(), person.getFirstName(), person.getBirthday(), person.getGender().toString()};
        int idPerson = patientRepository.insert(Constants.ADD_PERSON, objects);
        if (idPerson>=0){
            person.setId(idPerson);
            int idPatient = patientRepository.insert(Constants.ADD_PATIENT, idPerson);
            return idPatient >=0;
        }
        return false;
    }
    public boolean delete(Patient patient) throws DBException {
        return patientRepository.delete(Constants.DELETE_PATIENT, patient.getId());
    }

    @Override
    protected Patient readByResultSet(ResultSet rs) throws SQLException {
        while(rs.next()){
            Person person = getPerson(rs);
            Patient patient = Patient.createPatient(person);
            patient.setId(rs.getInt(2));
            return patient;
        }
        return null;
    }

    @Override
    protected List<Patient> findByResultSet(ResultSet rs) throws SQLException {
        List<Patient> list = new ArrayList<>();
        while(rs.next()){
            Person person = getPerson(rs);
            Patient patient = Patient.createPatient(person);
            patient.setId(rs.getInt(2));
            list.add(patient);
        }
        return list;
    }
}

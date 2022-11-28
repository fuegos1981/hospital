package com.epam.hospital.repository;

public class Constants {
    public static final String SETTINGS_FILE = "app.properties";

    public static final String GET_ALL_USERS = "select * from users order by login";
    public static final String GET_USER_BY_LOGIN = "select * from users where login= ?";

    public static final String ADD_PERSON = "INSERT INTO person (last_Name,first_Name,birthday,gender_id) VALUES (?,?,?,?)";
    //patient
    public static final String GET_Patient_BY_ID = "select patient.id, person_id, last_Name,first_Name, birthday," +
            "gender.name from patient join person on person.id=patient.person_id join gender on person.gender_id=gender.id where patient.id= ?";
    public static final String GET_ALL_PATIENTS = "select patient.id, person_id, last_Name,first_Name, birthday, " +
            "gender.name from patient join person on person.id=patient.person_id join gender on person.gender_id=gender.id";
    public static final String ADD_PATIENT = "INSERT INTO patient (person_id) VALUES (?)";
    public static final String DELETE_PATIENT = "DELETE from patient where id= ?";

    //doctor
    public static final String GET_DOCTOR_BY_ID = "select doctor.id, person_id, last_Name,first_Name, birthday,gender.name, category.name  from doctor " +
            "join person on person.id=doctor.person_id " +
            "join category on category.id=doctor.category " +
            "join gender on person.gender_id=gender.id where patient.id= ?";

    public static final String GET_ALL_DOCTORS = "select doctor.id, person_id, last_Name,first_Name, birthday, gender.name, category.name from doctor " +
            "join person on person.id=patient.person_id " +
            "join category on category.id=doctor.category " +
            "join gender on person.gender_id=gender.id";
    public static final String ADD_DOCTOR = "INSERT INTO doctor (person_id, category_id) VALUES (?,?)";
    public static final String DELETE_DOCTOR = "DELETE from doctor where id= ?";
}

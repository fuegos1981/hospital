package com.epam.hospital.repository;

public class Constants {
    public static final String SETTINGS_FILE = "app.properties";

    //user
    public static final String GET_ALL_USERS = "select * from users order by login";
    public static final String GET_USER_BY_LOGIN = "select * from users where login= ?";
    //person
    public static final String ADD_PERSON = "INSERT INTO person (last_Name,first_Name,birthday,gender_id) VALUES (?,?,?,?)";
    //patient
    public static final String GET_Patient_BY_ID = "select patient.id, person_id, last_Name,first_Name, birthday, email," +
            "gender.name as gender_name from patient join person on person.id=patient.person_id join gender on person.gender_id=gender.id where patient.id= ?";
    public static final String GET_ALL_PATIENTS = "select patient.id, person_id, last_Name,first_Name, birthday, email," +
            "gender.name as gender_name from patient join person on person.id=patient.person_id join gender on person.gender_id=gender.id order by last_Name,first_Name";
    public static final String ADD_PATIENT = "INSERT INTO patient (person_id) VALUES (?)";
    public static final String DELETE_PATIENT = "DELETE from patient where id= ?";

    //doctor
    public static final String GET_DOCTOR_BY_ID = "select doctor.id, person_id, last_Name,first_Name, birthday, email, gender.name as gender_name, category.name  from doctor " +
            "join person on person.id=doctor.person_id " +
            "join category on category.id=doctor.category_id " +
            "join gender on person.gender_id=gender.id where doctor.id= ?";

    public static final String GET_ALL_DOCTORS = "select doctor.id, person_id, last_Name,first_Name, birthday, email, gender.name as gender_name, category.name from doctor " +
            "join person on person.id=doctor.person_id " +
            "join category on category.id=doctor.category_id " +
            "join gender on person.gender_id=gender.id  order by last_Name,first_Name";

    public static final String ADD_DOCTOR = "INSERT INTO doctor (person_id, category_id) VALUES (?,?)";
    public static final String DELETE_DOCTOR = "DELETE from doctor where id= ?";

    //schedule
    public static final String GET_SCHEDULE_BY_ID = "select * from schedule where id= ?";
    public static final String GET_ALL_SCHEDULE = "select * from schedule order by id";
    public static final String ADD_SCHEDULE = "INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (?,?,?)";
    public static final String DELETE_SCHEDULE = "DELETE from schedule where id= ?";

    //appointment
    public static final String GET_APPOINTMENT_BY_ID = "select * from appointment where id= ?";
    public static final String GET_ALL_APPOINTMENTS = "select * from appointment order by id";
    public static final String GET_ALL_APPOINTMENTS_BY_PATIENT = "select * from appointment where patient_id= ?";
    public static final String ADD_APPOINTMENT = "INSERT INTO appointment (date_create,diagnosis_id,patient_id, doctor_id) VALUES (?,?,?,?)";
    public static final String DELETE_APPOINTMENT = "DELETE from appointment where id= ?";


    //appointment card
    public static final String GET_ALL_APPOINTMENTCARDS = "SELECT id, appointment_id,medication_id as medical_id, medication.name as medical_name, description, 1 as type FROM appointment_medication join medication where appointment_id=? and medication.id=medication_id\n" +
            "union SELECT id, appointment_id,procedure_id as medical_id, procedures.name as medical_name, description, 2 as type FROM appointment_procedure join procedures where appointment_id=? and procedures.id=procedure_id\n" +
            "union SELECT id, appointment_id,operation_id as medical_id, operation.name as medical_name, description, 3 as type FROM appointment_operation join operation where appointment_id=? and operation.id=operation_id; \n";

    public static final String ADD_APPOINTMENTCARD = "INSERT INTO appointment (date_create,diagnosis_id,patient_id, doctor_id) VALUES (?,?,?,?)";
    public static final String DELETE_APPOINTMENTCARD = "DELETE from appointment where id= ?";
    public static final String GET_ALL_CARDS_BY_APPOINTMENT = "";
    public static final String ADD_CARD_MEDICATION = "INSERT INTO appointment_medication (appointment_id,medication_id, description) VALUES (?,?,?)";
    public static final String DELETE_CARD_MEDICATION_BY_APPOINTMENT = "DELETE from appointment_medication appointment_id= ?";
    public static final String ADD_CARD_PROCEDURE = "INSERT INTO appointment_procedure (appointment_id,procedure_id, description) VALUES (?,?,?)";
    public static final String DELETE_CARD_PROCEDURE_BY_APPOINTMENT ="DELETE from procedure_medication appointment_id= ?";
    public static final String ADD_CARD_OPERATION = "INSERT INTO appointment_operation (appointment_id,operation_id, description) VALUES (?,?,?)";
    public static final String DELETE_CARD_OPERATION_BY_APPOINTMENT ="DELETE from appointment_operation appointment_id= ?";

    //category
    public static final String GET_CATEGORY_BY_NAME = "select * from category where name= ?";
    public static final String GET_ALL_CATEGORIES = "select * from category order by name";
    public static final String ADD_CATEGORY = "INSERT INTO category (name) VALUES (?)";
    public static final String DELETE_CATEGORY = "DELETE from category where name= ?";

    //diagnosis
    public static final String GET_DIAGNOSIS_BY_NAME = "select * from diagnosis where name= ?";
    public static final String GET_ALL_DIAGNOSISES = "select * from diagnosis order by name";
    public static final String ADD_DIAGNOSIS = "INSERT INTO diagnosis (name) VALUES (?)";
    public static final String DELETE_DIAGNOSIS = "DELETE from diagnosis where name= ?";

    //medication
    public static final String GET_MEDICATION_BY_NAME = "select * from medication where name= ?";
    public static final String GET_ALL_MEDICATIONES = "select * from medication order by name";
    public static final String ADD_MEDICATION = "INSERT INTO medication (name) VALUES (?)";
    public static final String DELETE_MEDICATION = "DELETE from medication where name= ?";

    //operation
    public static final String GET_OPERATION_BY_NAME = "select * from operation where name= ?";
    public static final String GET_ALL_OPERATIONES = "select * from operation order by name";
    public static final String ADD_OPERATION = "INSERT INTO operation (name) VALUES (?)";
    public static final String DELETE_OPERATION = "DELETE from operation where name= ?";

    //procedure
    public static final String GET_PROCEDURE_BY_NAME = "select * from procedure where name= ?";
    public static final String GET_ALL_PROCEDURES = "select * from procedure order by name";
    public static final String ADD_PROCEDURE = "INSERT INTO procedure (name) VALUES (?)";
    public static final String DELETE_PROCEDURE = "DELETE from procedure where name= ?";

    public static final String CATEGORY = "Category";
    public static final String DIAGNOSIS = "Diagnosis";
    public static final String MEDICATION = "Medication";
    public static final String PROCEDURE = "Procedure";
    public static final String OPERATION = "Operation";


    public static String[] getQueries(String toString) {
        if (toString.equalsIgnoreCase(CATEGORY))
            return new String[]{GET_CATEGORY_BY_NAME, GET_ALL_CATEGORIES,ADD_CATEGORY,DELETE_CATEGORY};
        else if(toString.equalsIgnoreCase(DIAGNOSIS))
            return new String[]{GET_DIAGNOSIS_BY_NAME, GET_ALL_DIAGNOSISES,ADD_DIAGNOSIS,DELETE_DIAGNOSIS};
        else if(toString.equalsIgnoreCase(MEDICATION))
            return new String[]{GET_MEDICATION_BY_NAME, GET_ALL_MEDICATIONES,ADD_MEDICATION,DELETE_MEDICATION};
        else if(toString.equalsIgnoreCase(OPERATION))
            return new String[]{GET_OPERATION_BY_NAME, GET_ALL_OPERATIONES,ADD_OPERATION,DELETE_OPERATION};
        else if(toString.equalsIgnoreCase(PROCEDURE))
            return new String[]{GET_PROCEDURE_BY_NAME, GET_ALL_PROCEDURES,ADD_PROCEDURE,DELETE_PROCEDURE};

        return null;

    }
}

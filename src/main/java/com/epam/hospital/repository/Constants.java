package com.epam.hospital.repository;

public class Constants {
    public static final String SETTINGS_FILE = "app.properties";

    //patient
    public static final String GET_Patient_BY_ID = "select patient.id, last_name,first_name, birthday, email," +
            "gender.name as gender_name from patient join gender on patient.gender_id=gender.id where patient.id= ?";
    public static final String GET_ALL_PATIENTS = "select patient.id, last_name,first_name, birthday, email," +
            "gender.name as gender_name from patient join gender on patient.gender_id=gender.id";
    public static final String ADD_PATIENT = "INSERT INTO patient (last_name,first_name, birthday, email,gender_id) VALUES (?,?,?,?,?)";
    public static final String UPDATE_PATIENT = "UPDATE patient SET last_name=?,first_name=?, birthday=?, email=?,gender_id=? where id=?";
    public static final String DELETE_PATIENT = "DELETE from patient where id= ?";
    public static final String GET_SIZE_PATIENT = "SELECT COUNT(id) FROM patient";
    //doctor
    public static final String GET_DOCTOR_BY_ID = "select doctor.id, last_name,first_name, login, password, roles.name as role_name, category.name as category_name  from doctor " +
            "join roles on roles.id=doctor.role_id " +
            "join category on category.id=doctor.category_id  where doctor.id= ?";
    public static final String GET_DOCTOR_BY_LOGIN = "select doctor.id, last_name,first_name, login, password, roles.name as role_name, category.name as category_name  from doctor " +
            "join roles on roles.id=doctor.role_id " +
            "join category on category.id=doctor.category_id  where doctor.login= ?";
    public static final String GET_ALL_DOCTORS = "select doctor.id, last_name,first_name, login, password, roles.name as role_name, category.name as category_name, count_patients from doctor " +
            "join category on category.id=doctor.category_id " +
            "join roles on roles.id=doctor.role_id "+
            " left join (select Count(distinct patient_id) as count_patients,doctor_id  from schedule group by doctor_id)as sch on sch.doctor_id= doctor.id";

    public static final String ADD_DOCTOR = "INSERT INTO doctor (last_name,first_name, category_id, login, password,role_id) VALUES (?,?,?,?,?,?)";
    public static final String DELETE_DOCTOR = "DELETE from doctor where id= ?";
    public static final String GET_SIZE_DOCTOR = "SELECT COUNT(id) FROM doctor";
    //schedule
    public static final String GET_SCHEDULE_BY_ID = "select * from schedule where id= ?";
    public static final String GET_SCHEDULE_BY_PATIENT_ID = "select * from schedule where patient_id= ?";
    public static final String GET_SCHEDULE_BY_DOCTOR_ID = "select * from schedule where doctor_id= ?";
    public static final String GET_ALL_SCHEDULE = "select * from schedule order by id";
    public static final String ADD_SCHEDULE = "INSERT INTO schedule (doctor_id, patient_id, visit_time) VALUES (?,?,?)";
    public static final String UPDATE_SCHEDULE = "UPDATE schedule Set doctor_id=?, patient_id=?, visit_time=? where id = ?";
    public static final String DELETE_SCHEDULE = "DELETE from schedule where id= ?";
    public static final String GET_SIZE_SCHEDULE = "SELECT COUNT(id) FROM schedule";
    //appointment
    public static final String GET_APPOINTMENT_BY_ID = "select * from appointment where id= ?";
    public static final String GET_ALL_APPOINTMENTS = "select * from appointment order by id";
    public static final String GET_ALL_APPOINTMENTS_BY_PATIENT = "select * from appointment where patient_id= ?";
    public static final String ADD_APPOINTMENT = "INSERT INTO appointment (date_create,diagnosis_id,patient_id, doctor_id) VALUES (?,?,?,?)";
    public static final String DELETE_APPOINTMENT = "DELETE from appointment where id= ?";
    public static final String GET_SIZE_APPOINTMENT = "SELECT COUNT(id) FROM appointment";

    //category
    public static final String GET_CATEGORY_BY_ID = "select * from category where id= ?";
    public static final String GET_ALL_CATEGORIES = "select * from category order by name";
    public static final String ADD_CATEGORY = "INSERT INTO category (name) VALUES (?)";
    public static final String DELETE_CATEGORY = "DELETE from category where name= ?";
    public static final String GET_SIZE_CATEGORY = "SELECT COUNT(id) FROM category";
    //diagnosis
    public static final String GET_DIAGNOSIS_BY_ID = "select * from diagnosis where id= ?";
    public static final String GET_ALL_DIAGNOSISES = "select * from diagnosis order by name";
    public static final String ADD_DIAGNOSIS = "INSERT INTO diagnosis (name) VALUES (?)";
    public static final String DELETE_DIAGNOSIS = "DELETE from diagnosis where name= ?";
    public static final String GET_SIZE_DIAGNOSIS = "SELECT COUNT(id) FROM diagnosis";
    //role
    public static final String GET_ROLE_BY_ID = "select * from roles where id= ?";
    public static final String GET_ALL_ROLES = "select * from roles order by name";

    public static final String CATEGORY = "Category";
    public static final String DIAGNOSIS = "Diagnosis";
    public static final String ROLE = "Role";



    public static String[] getQueries(String toString) {
        if (toString.equalsIgnoreCase(CATEGORY))
            return new String[]{GET_CATEGORY_BY_ID, GET_ALL_CATEGORIES,ADD_CATEGORY,DELETE_CATEGORY,GET_SIZE_CATEGORY};
        else if(toString.equalsIgnoreCase(DIAGNOSIS))
            return new String[]{GET_DIAGNOSIS_BY_ID, GET_ALL_DIAGNOSISES,ADD_DIAGNOSIS,DELETE_DIAGNOSIS,GET_SIZE_DIAGNOSIS};
        else if(toString.equalsIgnoreCase(ROLE))
            return new String[]{GET_ROLE_BY_ID, GET_ALL_ROLES,null, null};
        return null;

    }
}

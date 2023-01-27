package com.epam.hospital.controller;

public interface ControllerConstants{
    String SUBMIT_US = "submit_us";
    String SUBMIT_UA = "submit_ua";
    String SUBMIT = "submit";
    String LOCALE_US = "en-US";
    String LOCALE_UA = "uk-UA";
    String LOCALE = "locale";
    String ID = "id";
    String NAME = "name";
    String DOCTORS = "doctors";
    String PATIENTS = "patients";
    String DOCTOR_ID = "doctor_id";
    String PATIENT_ID = "patient_id";
    String DIAGNOSISES = "diagnosises";
    String DIAGNOSIS_ID = "diagnosis_id";
    String MESSAGE = "message";
    String ROLE = "role";
    String NURSE = "nurse";
    String GENDER = "gender";
    String MALE = "male";
    String ERROR_LOG_PASS = "error_log_pass";
    String CATEGORY_ID = "category_id";
    String CATEGORIES = "categories";
    String ROLE_ID = "role_id";
    String ROLES = "roles";
    String SIMPLE = "simple";


    //pages
    String PAGE_INDEX = "/index.jsp";
    String PAGE_ADD_SCHEDULE = "/WEB-INF/pages/add-schedule.jsp";
    String PAGE_ERROR = "/WEB-INF/pages/error.jsp";
    String PAGE_EDIT_PATIENT = "/WEB-INF/pages/edit-patient.jsp";
    String PAGE_EDIT_DOCTOR = "/WEB-INF/pages/edit-doctor.jsp";
    String PAGE_ADMIN = "/WEB-INF/pages/adminInterface.jsp";
    String PAGE_EDIT_SIMPLE = "/WEB-INF/pages/edit-simple.jsp";
    String PAGE_EDIT_APPOINTMENT = "/WEB-INF/pages/edit-appointment.jsp";
    String PAGE_PATIENT_INFO = "/WEB-INF/pages/patient-info.jsp";
    String PAGE_MEDIC = "/WEB-INF/pages/medic-interface.jsp";
    String PAGE_SIMPLE = "/WEB-INF/pages/work_simple.jsp";

    int MAX_COUNT_ON_PAGE = 10;
}

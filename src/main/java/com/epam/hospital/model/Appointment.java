package com.epam.hospital.model;

import java.util.Date;
import java.util.List;

public class Appointment {
    private int id;
    private Date dateCreate;
    private Diagnosis diagnosis;
    private Patient patient;
    private Doctor doctor;
    private String medication;
    private String procedure;
    private String operation;

    public static Appointment createAppointment(Date dateCreate, Diagnosis diagnosis, Patient patient, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setDateCreate(dateCreate);
        appointment.setDiagnosis(diagnosis);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointment;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

}

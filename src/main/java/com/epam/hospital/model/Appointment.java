package com.epam.hospital.model;

import java.util.Date;
import java.util.List;

public class Appointment {
    private int id;
    private Date dateCreate;
    private Diagnosis diagnosis;
    private Patient patient;
    private Doctor doctor;
    private List<AppointmentCard> listCard;

    public static Appointment createAppointment(Date dateCreate,Diagnosis diagnosis,Patient patient, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setDateCreate(dateCreate);
        appointment.setDiagnosis(diagnosis);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointment;
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

    public void setListCard(List<AppointmentCard> listCard) {
        this.listCard = listCard;
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

    public List<AppointmentCard> getListCard() {
        return listCard;
    }
}

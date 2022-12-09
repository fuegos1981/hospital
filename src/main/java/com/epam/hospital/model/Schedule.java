package com.epam.hospital.model;

import java.util.Date;

public class Schedule {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private Date dateVisit;

    public static Schedule createSchedule(Doctor doctor, Patient patient,Date dateVisit){
        Schedule schedule = new Schedule();
        schedule.doctor=doctor;
        schedule.patient=patient;
        schedule.dateVisit = dateVisit;
        return schedule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public int getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getDateVisit() {
        return dateVisit;
    }
}

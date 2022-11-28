package com.epam.hospital.model;

import java.util.Date;

public class Schedule {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private Date date;

    public static Schedule createSchedule(Doctor doctor, Patient patient,Date date){
        Schedule schedule = new Schedule();
        schedule.doctor=doctor;
        schedule.patient=patient;
        schedule.date = date;
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

    public void setDate(Date date) {
        this.date = date;
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

    public Date getDate() {
        return date;
    }
}

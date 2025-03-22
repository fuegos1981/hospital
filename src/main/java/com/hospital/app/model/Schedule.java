package com.hospital.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

}

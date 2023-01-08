package com.epam.hospital.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

    public Appointment() {
        this.setMedication("");
        this.setProcedure("");
        this.setOperation("");
    }

}

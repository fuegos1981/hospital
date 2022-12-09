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

}

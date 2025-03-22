package com.hospital.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Patient {
    private int id;
    private String lastName;
    private String firstName;
    private Date birthday;
    private String email;
    private Gender gender;

    public static Patient createPatient(String lastName, String firstName, Date birthday, String email, Gender gender){
        Patient patient = new Patient();
        patient.setLastName(lastName);
        patient.setFirstName(firstName);
        patient.setBirthday(birthday);
        patient.setEmail(email);
        patient.setGender(gender);
        return patient;
    }

    @Override
    public String toString() {
        return lastName +" "+ firstName;
    }
}

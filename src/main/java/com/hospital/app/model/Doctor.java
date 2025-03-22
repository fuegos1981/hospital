package com.hospital.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doctor {
    private int id;
    private String lastName;
    private String firstName;
    private Category category;
    private String login;
    private String password;
    private Role role;

    public static Doctor createDoctor(String lastName, String firstName, Category category) {
        Doctor doctor = new Doctor();
        doctor.setLastName(lastName);
        doctor.setFirstName(firstName);
        doctor.setCategory(category);
        return  doctor;
    }

    @Override
    public String toString() {
        return lastName +" "+firstName+", " + category;
    }

}

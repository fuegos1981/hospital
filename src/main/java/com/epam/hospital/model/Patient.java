package com.epam.hospital.model;

public class Patient {
    private int id;
    private Person person;

    public static Patient createPatient(Person person) {
       Patient patient = new Patient();
       patient.setPerson(person);
       return  patient;
    }

    @Override
    public String toString() {
        return person.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

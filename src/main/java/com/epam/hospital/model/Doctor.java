package com.epam.hospital.model;

public class Doctor {
    private int id;
    private Person person;
    private Category category;

    public static Doctor createDoctor(Person person, Category category) {
        Doctor doctor = new Doctor();
        doctor.setPerson(person);
        doctor.setCategory(category);
        return  doctor;
    }

    @Override
    public String toString() {
        return person +", " + category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

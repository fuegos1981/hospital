package com.epam.hospital.model;

import java.util.Date;

public class Person {
    private int id;
    private String lastName;
    private String firstName;
    private Date birthday;
    private String email;
    private Gender gender;

    @Override
    public String toString() {
        return lastName +" "+ firstName;
    }

    public String getEmail() {
        return email;
    }

    public static Person createPerson(String lastName, String firstName, Date birthday, String email, Gender gender){
        Person person = new Person();
        person.lastName=lastName;
        person.firstName=firstName;
        person.birthday = birthday;
        person.email = email;
        person.gender = gender;

        return person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

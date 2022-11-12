package com.epam.hospital.db.entity;

import java.util.Date;

public class Person {
    private int id;
    private String lastName;
    private String firstName;
    private Date birthday;
    private Gender gender;

    public static Person createPerson(String lastName, String firstName){
        Person person = new Person();
        person.lastName=lastName;
        person.firstName=firstName;
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

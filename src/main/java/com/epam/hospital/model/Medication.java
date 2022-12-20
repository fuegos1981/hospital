package com.epam.hospital.model;

public class Medication implements SimpleModel,Medical{
    private int id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public static Medication createInstance(String name){
        Medication medication = new Medication();
        medication.name=name;
        return medication;
    }
}

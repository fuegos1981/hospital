package com.epam.hospital.model;

public class Diagnosis implements SimpleModel{
    private int id;
    private String name;

    public static Diagnosis createInstance(String name){
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.name=name;
        return diagnosis;
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

    public void setName(String name) {
        this.name = name;
    }

}

package com.epam.hospital.model;

public class Procedure implements SimpleModel,Medical{
    private int id;
    private String name;

    public static Procedure createInstance(String name){
        Procedure procedure = new Procedure();
        procedure.name=name;
        return procedure;
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

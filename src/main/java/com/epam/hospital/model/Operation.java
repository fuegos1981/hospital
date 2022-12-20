package com.epam.hospital.model;

public class Operation implements SimpleModel,Medical{
    private int id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public static Operation createInstance(String name){
        Operation operation = new Operation();
        operation.name=name;
        return operation;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }
}

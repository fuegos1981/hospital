package com.epam.hospital.model;

public class Operation implements SimpleModel,Medical{
    private int id;
    private String name;

    public static Operation createInstance(String name){
        Operation operation = new Operation();
        operation.name=name;
        return operation;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}

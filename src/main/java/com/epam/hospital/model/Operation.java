package com.epam.hospital.model;

public class Operation {
    private int id;
    private String name;

    public static Operation createOperation(String name){
        Operation operation = new Operation();
        operation.name=name;
        return operation;
    }
}

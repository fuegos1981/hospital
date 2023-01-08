package com.epam.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diagnosis implements SimpleModel{
    private int id;
    private String name;

    public static Diagnosis createInstance(String name){
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.name=name;
        return diagnosis;
    }

    @Override
    public String toString() {
        return name;
    }
}

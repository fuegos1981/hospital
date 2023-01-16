package com.epam.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diagnosis implements SimpleModel{
    private int id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}

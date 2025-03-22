package com.hospital.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category implements SimpleModel{
    private int id;
    private String name;

    public Category() {
    }

    @Override
    public String toString() {
        return name;
    }

}

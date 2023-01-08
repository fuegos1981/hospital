package com.epam.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category implements SimpleModel{
    private int id;
    private String name;

    public Category() {
    }

    public static Category createInstance(String name){
        Category category = new Category();
        category.name=name;
        return category;
    }

    @Override
    public String toString() {
        return name;
    }

}

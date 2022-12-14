package com.epam.hospital.model;

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

    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

package com.epam.hospital.model;

public class Category {
    private int id;
    private String name;

    public static Category createCategory(String name){
        Category category = new Category();
        category.name=name;
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

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

package com.epam.hospital.db.entity;

public class Category {
    private int id;
    private String name;
    private String nameRu;

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

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameRu() {
        return nameRu;
    }
}

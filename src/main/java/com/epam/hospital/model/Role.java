package com.epam.hospital.model;

public class Role {
    private int id;
    private String name;

    public static Role createInstance(String name){
        Role role = new Role();
        role.name=name;
        return role;
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

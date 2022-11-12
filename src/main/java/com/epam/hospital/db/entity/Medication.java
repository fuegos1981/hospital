package com.epam.hospital.db.entity;

public class Medication {
    private int id;
    private String name;
    private String nameRu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public static Medication createMedication(String name){
        Medication medication = new Medication();
        medication.name=name;
        return medication;
    }
}

package com.epam.hospital.db.entity;

public class Procedure {
    private int id;
    private String name;
    private String nameRu;

    public static Procedure createProcedure(String name){
        Procedure procedure = new Procedure();
        procedure.name=name;
        return procedure;
    }

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
}

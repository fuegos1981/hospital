package com.hospital.app.model;

public enum Gender {
    MALE, FEMALE;
    public static int  getID(Gender gender){
        if (gender==MALE) return 1;
        else return 2;
    }
}

package com.hospital.app.model;

public enum Role {
        ADMIN, DOCTOR, NURSE;

public static int  getID(Role role){
        if (role==ADMIN) return 1;
        else if (role==DOCTOR) return 2;
        else return 3;
        }
}

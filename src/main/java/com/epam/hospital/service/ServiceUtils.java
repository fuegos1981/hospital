package com.epam.hospital.service;

import com.epam.hospital.service.impl.ValidateException;

import java.util.Date;

public class ServiceUtils {
    public static void nameValidate(String name, String value) throws ValidateException {
        if (value==null||!value.matches("[A-ZА-Я][a-zа-я]*")){
            throw new ValidateException(name);
        }
    }

    public static void birthdayValidate(String patientBirthday, Date birthday) throws ValidateException {
        if (birthday==null||birthday.after(new Date())){
            throw new ValidateException(patientBirthday);
        }

    }
    public static void dateVisitValidate(String nameDateVisit, Date dateVisit) throws ValidateException {
        if (dateVisit==null||dateVisit.before(new Date())){
            throw new ValidateException(nameDateVisit);
        }

    }
}

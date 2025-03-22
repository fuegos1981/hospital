package com.hospital.app.service;

import com.hospital.app.exceptions.ValidateException;

import java.util.Date;

public class ValidatorUtils {
    public static final String email_valid = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String name_valid = "[A-ZА-Я][a-zа-я]*";

    public static void nameValidate(String name, String value) throws ValidateException {
        if (value == null || !value.matches(name_valid)) {
            throw new ValidateException(name);
        }
    }

    public static void emailValidate(String email, String value) throws ValidateException {
        if (value == null || !value.matches(email_valid)) {
            throw new ValidateException(email);
        }
    }

    public static void birthdayValidate(String patientBirthday, Date birthday) throws ValidateException {
        if (birthday == null || birthday.after(new Date())) {
            throw new ValidateException(patientBirthday);
        }

    }

    public static void dateVisitValidate(String nameDateVisit, Date dateVisit) throws ValidateException {
        if (dateVisit == null || dateVisit.before(new Date())) {
            throw new ValidateException(nameDateVisit);
        }

    }
}

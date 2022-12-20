package com.epam.hospital.service;

import com.epam.hospital.service.impl.ValidateException;

public class ServiceUtils {
    public static void nameValidate(String name, String value) throws ValidateException {
        if (!value.matches("[A-ZА-Я][a-zа-я]*")){
            throw new ValidateException("not correct:"+name);
        }
    }

}

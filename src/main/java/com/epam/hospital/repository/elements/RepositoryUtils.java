package com.epam.hospital.repository.elements;


import com.epam.hospital.model.SimpleModel;


public class RepositoryUtils {

    public static SimpleModel getSimpleInstance(String classNameParam){
        try {
            Class<?> clazz = Class.forName("com.epam.hospital.model."+classNameParam);
            SimpleModel  simpleModel= (SimpleModel) clazz.getConstructor().newInstance();
            return simpleModel;
        }
        catch( Exception e ) {
            return null;
        }

    }
}

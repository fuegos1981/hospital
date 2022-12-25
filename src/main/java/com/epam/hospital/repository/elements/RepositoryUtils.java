package com.epam.hospital.repository.elements;


import com.epam.hospital.model.SimpleModel;

import java.util.Map;
import java.util.stream.Collectors;


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
    public static String getSelectionString(Map<String, Integer> selection){
        if (selection.size()==0) return "";
        return  " where "+selection.keySet().stream()
                .map(i -> i+" = ?")
                .collect(Collectors.joining(" and "));
    }
}

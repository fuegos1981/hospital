package com.hospital.app.model;

/**
 * This interface is intended  for all models that have only one single field Name (category, diagnosis, role).
 * @author Sinkevych Olena
 *
 */
public interface SimpleModel {
    void setId(int id);
    int getId();
    void setName(String name);
    String getName();

    static SimpleModel getSimpleInstance(String classNameParam){
        try {
            Class<?> clazz = Class.forName("com.hospital.app.model."+classNameParam);
            return (SimpleModel) clazz.getConstructor().newInstance();
        }
        catch( Exception e ) {
            return null;
        }
    }
}

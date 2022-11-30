package com.epam.hospital.repository.elements;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryUtils {
    public static  Person getPerson(ResultSet rs) throws SQLException {
        Person person = Person.createPerson(rs.getString(3),
                rs.getString(4),
                rs.getDate(5),
                rs.getString(6),
                Gender.valueOf(rs.getString(6).toUpperCase()));
        person.setId(rs.getInt(2));
        return person;
    }
}

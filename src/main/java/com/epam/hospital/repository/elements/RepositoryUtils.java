package com.epam.hospital.repository.elements;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Person;
import com.epam.hospital.repository.Fields;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryUtils {
    public static  Person getPerson(ResultSet rs) throws SQLException {
        Person person = Person.createPerson(
                rs.getString(Fields.PERSON_LAST_NAME),
                rs.getString(Fields.PERSON_FIRST_NAME),
                rs.getDate(Fields.PERSON_BIRTHDAY),
                rs.getString(Fields.PERSON_EMAIL),
                Gender.valueOf(rs.getString(Fields.PERSON_GENDER).toUpperCase()));
        person.setId(rs.getInt(Fields.PERSON_ID));
        return person;
    }
}

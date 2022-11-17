package com.epam.hospital.repository;

public class Constants {
    public static final String SETTINGS_FILE = "app.properties";

    public static final String GET_ALL_USERS = "select * from users order by login";
    public static final String GET_USER_BY_LOGIN = "select * from users where login= ?";
}

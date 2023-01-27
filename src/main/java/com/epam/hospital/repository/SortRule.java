package com.epam.hospital.repository;

public enum SortRule {
    NAME_ASC (" order by last_Name,first_Name"),
    NAME_DESC(" order by last_Name desc,first_Name desc"),
    CATEGORY_ASC(" order by category_name,last_Name,first_Name"),
    CATEGORY_DESC(" order by category_name desc,last_Name,first_Name"),
    COUNT_PATIENT_ASC(" order by count_patients,last_Name,first_Name"),
    COUNT_PATIENT_DESC(" order by count_patients desc, last_Name, first_Name"),
    BIRTHDAY_ASC(" order by birthday,last_Name,first_Name"),
    BIRTHDAY_DESC(" order by birthday desc,last_Name,first_Name"),
    DATE_CREATE_DESC(" order by date_create desc"),
    NAME_SIMPLE_ASC (" order by name"),
    VISIT_TIME_DESC(" order by visit_time desc");


    private  final String query;

    public String getQuery() {
        return query;
    }

    SortRule(String query) {
        this.query = query;
    }
}

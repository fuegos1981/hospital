package com.epam.hospital.service;

import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.exceptions.ValidateException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Service<T> {

    T readById(Integer id) throws DBException, SQLException, ValidateException;
    boolean create(T t) throws DBException, ValidateException;
    boolean update(T t) throws DBException, ValidateException;
    void delete(T t) throws DBException;
    List<T> getAll(Map<String,Integer> selection, SortRule sortRule, int[] limit) throws DBException, SQLException;
    int getSize(Map<String,Integer> selection) throws DBException;

}

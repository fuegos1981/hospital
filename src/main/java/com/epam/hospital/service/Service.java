package com.epam.hospital.service;

import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.repository.QueryRedactor;
import com.epam.hospital.repository.SortRule;
import com.epam.hospital.exceptions.ValidateException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Service<T> {

    T readById(Integer id) throws DBException, SQLException, ValidateException;
    boolean create(T t) throws DBException, ValidateException, SQLException;
    boolean update(T t) throws DBException, ValidateException, SQLException;
    void delete(T t) throws DBException, ValidateException, SQLException;
    List<T> getAll(QueryRedactor qr) throws DBException, SQLException;
    int getSize(QueryRedactor qr) throws DBException;
    List<T> getAll() throws DBException, SQLException;
    int getSize() throws DBException;
}

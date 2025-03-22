package com.hospital.app.service;

import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.QueryRedactor;
import com.hospital.app.exceptions.ValidateException;

import java.sql.SQLException;
import java.util.List;

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

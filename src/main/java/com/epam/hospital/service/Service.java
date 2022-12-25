package com.epam.hospital.service;

import com.epam.hospital.repository.DBException;
import com.epam.hospital.service.impl.ValidateException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Service<T> {
    boolean create(T t) throws DBException, ValidateException;
    T readById(Integer id) throws DBException, SQLException, ValidateException;
    boolean update(T t) throws DBException, ValidateException;
    void delete(T t) throws DBException;
    List<T> getAll(int[] limit, String sortRule) throws DBException, SQLException;
    int getSize() throws DBException;
}

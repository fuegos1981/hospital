package com.epam.hospital.service;

import com.epam.hospital.model.User;
import com.epam.hospital.repository.DBException;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {
    boolean create(T t) throws DBException;
    T readById(int id) throws DBException, SQLException;
    boolean update(T t) throws DBException;
    void delete(T t) throws DBException;
    List<T> getAll() throws DBException, SQLException;
}

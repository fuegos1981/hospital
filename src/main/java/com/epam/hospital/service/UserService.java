package com.epam.hospital.service;

import com.epam.hospital.db.entity.User;
import com.epam.hospital.repository.DBException;

import java.util.List;

public interface UserService {
    User create(User user);
    User readByLogin(String login) throws DBException;
    User update(User role);
    void delete(long id);
    List<User> getAll();
}

package com.epam.hospital.service;

import com.epam.hospital.db.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(int id);
    User update(User role);
    void delete(long id);
    List<User> getAll();
}

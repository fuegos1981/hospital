package com.epam.hospital.service.impl;

import com.epam.hospital.model.User;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.UserRepository;
import com.epam.hospital.service.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserService implements Service<User> {
    private static Service<User> userService;
    private UserRepository userRepository;

    private UserService() {
        this.userRepository = UserRepository.getUserRepository();
    }

    public static Service getUserService(){
        return Objects.requireNonNullElseGet(userService, UserService::new);
    }

    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public User readById(int id) throws DBException, SQLException {
        return null;
    }

    public User readByName(String login) throws DBException {
        return userRepository.readByLogin(login);
    }

    @Override
    public boolean update(User user) {
        return true;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}

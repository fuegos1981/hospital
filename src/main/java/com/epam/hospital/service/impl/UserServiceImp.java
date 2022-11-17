package com.epam.hospital.service.impl;

import com.epam.hospital.db.entity.User;
import com.epam.hospital.repository.DBException;
import com.epam.hospital.repository.UserRepository;
import com.epam.hospital.service.UserService;

import java.util.List;
import java.util.Objects;

public class UserServiceImp implements UserService {
    private static UserService userService;
    private UserRepository userRepository;

    private UserServiceImp() {
        this.userRepository = UserRepository.getUserRepository();
    }

    public static UserService getUserService(){
        return Objects.requireNonNullElseGet(userService, UserServiceImp::new);
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User readByLogin(String login) throws DBException {
        return userRepository.readByLogin(login);
    }

    @Override
    public User update(User role) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}

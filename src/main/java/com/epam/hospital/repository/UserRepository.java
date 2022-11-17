package com.epam.hospital.repository;

import com.epam.hospital.db.entity.Role;
import com.epam.hospital.db.entity.User;

import java.sql.*;
import java.util.Objects;

public class UserRepository {
    private static UserRepository  userRepository;
    private static ConnectionPool connectionPool;

    private UserRepository() {
        connectionPool= ConnectionPool.getInstance();
    }
    public static UserRepository getUserRepository(){
        return Objects.requireNonNullElseGet(userRepository, UserRepository::new);
    }

    public User readByLogin(String login) throws DBException {
        try (Connection con= connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(Constants.GET_USER_BY_LOGIN)){
            stmt.setString(1,login);
            try (ResultSet rs=stmt.executeQuery()){
                while(rs.next()){
                    return new User(rs.getString(2),rs.getString(3), Role.createRole(rs.getString(4)));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException("Trouble with method readByLogin! ",e);
        }
        return null;
    }

}

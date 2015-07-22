package com.model.interfaces;

import com.model.beans.User;

import java.util.List;

/**
 * Created by michal on 29.06.15.
 */
public interface UserDao {

    void createOrUpdateUser(User user);

    void deleteUser(int id);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);
}
